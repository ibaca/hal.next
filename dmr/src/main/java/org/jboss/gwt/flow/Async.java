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

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import rx.Observable;
import rx.Single;

/** Flow control functions for GWT. Integrates with the default GWT scheduling mechanism. */
public class Async {

    private static <C> Single<C> fromControl(C context, Consumer<Control<C>> producer) {
        return Single.fromEmitter(emitter -> producer.accept(new Control<C>() {
            @Override public void proceed() { emitter.onSuccess(context); }
            @Override public void abort() { emitter.onError(new Exception("abort")); }
            @Override public C getContext() { return context; }
        }));
    }

    public static <C> Single<C> single(Progress progress, C context, Consumer<Control<C>> task) {
        progress.reset(1);
        return fromControl(context, task)
                .doOnSuccess(n -> progress.finish())
                .doOnError(e -> progress.finish());
    }

    @SafeVarargs
    public static <C> Single<C> series(Progress progress, C context, Consumer<Control<C>>... tasks) {
        return series(progress, context, asList(tasks));
    }

    public static <C> Single<C> series(Progress progress, C context, Collection<? extends Consumer<Control<C>>> tasks) {
        assert !tasks.isEmpty();
        progress.reset(tasks.size());
        return Observable.from(tasks)
                .concatMap(f -> fromControl(context, f).toObservable())
                .doOnNext(n -> progress.tick())
                .doOnTerminate(progress::finish)
                .doOnError(e -> progress.finish())
                .last().toSingle();
    }

    public static <C> Single<C> interval(Progress progress, C context, int interval, Predicate<C> until,
            Consumer<Control<C>> task) {
        progress.reset();
        return Observable.interval(interval, TimeUnit.MILLISECONDS)
                .flatMapSingle(n -> fromControl(context, task))
                .doOnNext(n -> progress.tick())
                .doOnTerminate(progress::finish)
                .doOnError(e -> progress.finish())
                .takeUntil(until::test)
                .last().toSingle();
    }
}
