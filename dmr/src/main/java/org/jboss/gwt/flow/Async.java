/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.gwt.flow;

import com.google.gwt.core.client.Scheduler;
import java.util.function.Predicate;

/** Flow control functions for GWT. Integrates with the default GWT scheduling mechanism. */
public class Async {

    /**
     * Convenience method to executes a single function. Use this method if you have implemented your business logic
     * across different functions, but just want to execute a single function.
     */
    public static <C> void single(Progress progress, C context, Outcome<C> outcome, Function<C> function) {
        progress.reset(1);
        function.execute(new Control<C>() {
            @Override public void proceed() { progress.finish(); outcome.onSuccess(context); }
            @Override public void abort() { progress.finish(); outcome.onFailure(context); }
            @Override public C getContext() { return context; }
        });
    }

    /**
     * Run an array of functions in series, each one running once the previous function has completed.
     * If any functions in the series pass an error to its callback,
     * no more functions are run and outcome for the series is immediately called with the value of the error.
     */
    @SafeVarargs
    public static <C> void series(Progress progress, C context, Outcome<C> outcome, Function<C>... functions) {
        class SequentialControl implements Control<C> {
            private Function<C> next;
            private int index;
            private boolean drained;
            private boolean aborted;
            private boolean pending;
            @Override public C getContext() { return context;}
            @Override public void proceed() {
                // start ticking *after* the first function has finished
                if (index > 0) progress.tick();
                if (index >= functions.length) { next = null;drained = true;}
                else { next = functions[index];index++;}
                this.pending = false;
            }
            @Override public void abort() { this.aborted = true;this.pending = false;}
            public boolean isAborted() { return aborted;}
            private boolean isDrained() { return drained;}
            private void nextUnlessPending() { if (!pending) { pending = true;next.execute(this); } }
        }
        SequentialControl ctrl = new SequentialControl();

        // reset progress
        progress.reset(functions.length);

        // select first function and start
        ctrl.proceed();
        Scheduler.get().scheduleIncremental(() -> {
            if (ctrl.isDrained()) {
                // schedule deferred so that 'return false' executes first!
                Scheduler.get().scheduleDeferred(() -> {
                    progress.finish();
                    outcome.onSuccess(context);
                });
                return false;
            } else if (ctrl.isAborted()) {
                // schedule deferred so that 'return false' executes first!
                Scheduler.get().scheduleDeferred(() -> {
                    progress.finish();
                    outcome.onFailure(context);
                });
                return false;
            } else {
                ctrl.nextUnlessPending();
                return true;
            }
        });
    }

    /**
     * Repeatedly call function optionally waiting {@code period} milñis between calls, while condition is met. Calls
     * the callback when stopped, or an error occurs.
     *
     * @param period any value below 100 is ignored!
     */
    @SuppressWarnings("Duplicates")
    public static <C> void whilst(Progress progress, C context, Predicate<C> predicate, Outcome<C> outcome,
            Function<C> function, int period) {
        class GuardedControl implements Control<C> {
            private boolean aborted;
            private boolean shouldProceed() { return predicate.test(context) && !aborted;}
            private boolean isAborted() { return aborted;}
            @Override public void proceed() {/* ignore*/}
            @Override public void abort() { this.aborted = true;}
            @Override public C getContext() { return context;}
        }
        final GuardedControl ctrl = new GuardedControl();
        progress.reset();

        Scheduler.RepeatingCommand repeatingCommand = () -> {
            if (!ctrl.shouldProceed()) {
                // schedule deferred so that 'return false' executes first!
                Scheduler.get().scheduleDeferred(() -> {
                    if (ctrl.isAborted()) {
                        progress.finish();
                        outcome.onFailure(context);
                    } else {
                        progress.finish();
                        outcome.onSuccess(context);
                    }
                });
                return false;
            } else {
                function.execute(ctrl);
                progress.tick();
                return true;
            }
        };

        if (period > 100) {
            Scheduler.get().scheduleFixedPeriod(repeatingCommand, period);
        } else {
            Scheduler.get().scheduleIncremental(repeatingCommand);
        }
    }
}
