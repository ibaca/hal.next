$wnd.hal.runAsyncCallback53("function GLh(){FLh()}\nfunction FLh(){FLh=OGc}\nfunction TLh(){TLh=OGc}\nfunction WLh(){WLh=OGc}\nfunction ZLh(){ZLh=OGc}\nfunction aMh(){aMh=OGc}\nfunction dMh(){dMh=OGc}\nfunction gMh(){gMh=OGc}\nfunction jMh(){jMh=OGc}\nfunction mMh(){mMh=OGc}\nfunction QMh(){QMh=OGc;SAe()}\nfunction uLh(){uLh=OGc;rCe()}\nfunction ILh(){ILh=OGc;Gb();ukk()}\nfunction tLh(){tLh=OGc;sLh=uc(rLh())}\nfunction ULh(a){TLh();this.a=a}\nfunction XLh(a){WLh();this.a=a}\nfunction eMh(a){dMh();this.a=a}\nfunction hMh(a){gMh();this.a=a}\nfunction nMh(a){mMh();this.a=a}\nfunction kMh(a,b){jMh();this.a=a;this.b=b}\nfunction bMh(a,b,d){aMh();this.a=a;this.b=b;this.c=d}\nfunction $Lh(a,b,d,e){ZLh();this.a=a;this.c=b;this.d=d;this.b=e}\nfunction oLh(a,b,d){rc.call(this,a,b);this.fOb();this.a=d}\nfunction eLh(a){bLh();VUe.call(this,a);this.aOb()}\nfunction qLh(a){mLh();return Dc((tLh(),sLh),a)}\nfunction yLh(a,b){uLh();return new SMh(b,a)}\nfunction xLh(a,b,d){uLh();a.Bpc((mJh(),eJh),'resource-adapter',new eMh(d))}\nfunction BLh(a,b){uLh();var d;{d=A5(b.kd()._L(new GLh).VL(ILd()),22);a.Ej(d)}}\nfunction rLh(){mLh();return _3(N3(z_b,1),{4:1,18:1,1:1,5:1},464,0,[jLh,kLh,lLh])}\nfunction KLh(a,b,d,e){ILh();this.a=a;this.b=b;this.c=d;this.d=e;Nb.call(this);this.mOb()}\nfunction mLh(){mLh=OGc;pc();jLh=new oLh('ARCHIVE',0,Wkd('Archive'));kLh=new oLh('MODULE',1,Wkd('Module'));lLh=new oLh('UNKNOWN',2,'n/a')}\nfunction CLh(a,b){uLh();var d,e,g,h,i;{e=b._R('archive');i=b._R('module');d=K5(e.Lf());h=K5(i.Lf());if(cl(d)&&cl(h)){g=new I_d;return Tne(a.N$c().bTc(g.ZO('archive'),g.ZO('module')))}return Pne(),One}}\nfunction wLh(a,b,d,e,g,h,i){uLh();tCe.call(this,(new sfk(a,'resource-adapter','Resource Adapter')).xtc(new ULh(e)).Etc().Dtc().Atc(new XLh(i)));this.hOb();this.G$(b.rsc((YCl(),YAl),'Resource Adapter',(mJh(),fJh),new $Lh(this,g,i,e)));this.t_(new bMh(this,d,h))}\nfunction SMh(a,b){QMh();var d,e;UAe.call(this,a.name);this.tOb();d=a.bOb();if(d6(d,(mLh(),jLh))){A5(A5(this.i$().fN(),3)._M(b.N$c().mWc(d.gOb(),a.cOb())),3).WM()}else if(d6(d,(mLh(),kLh))){A5(A5(this.i$().fN(),3)._M(b.N$c().mWc(d.gOb(),a.dOb())),3).WM()}e=(new clk(a,Gsd(_3(N3(Zib,1),{4:1,1:1,5:1,6:1},2,6,['statistics-enabled','transaction-support'])))).vvc();this.i$().JM(e)}\nMGc(900,10,{1:1,16:1,10:1},eLh);_.bOb=function fLh(){if(this.hasDefined('archive')){return mLh(),jLh}else if(this.hasDefined('module')){return mLh(),kLh}return mLh(),lLh};_.cOb=function gLh(){return this.hasDefined('archive')?this.get('archive').asString():null};_.dOb=function hLh(){return this.hasDefined('module')?this.get('module').asString():null};_.eOb=function iLh(){return this.hasDefined('transaction-support')};MGc(464,14,{4:1,17:1,14:1,1:1,464:1},oLh);_.fOb=function nLh(){};_.gOb=function pLh(){return this.a};var jLh,kLh,lLh;var z_b=cgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapter/AdapterType',464,Dib,rLh,qLh);var sLh;MGc(3432,39,{1:1,13:1,39:1},wLh);_.hOb=function vLh(){};_.iOb=function zLh(a,b,d,e){uLh();var g,h,i;{i=a.yJc((mJh(),fJh));h=(new Vsk((YCl(),_Al),i)).Oxc().byc(new lpk).Qxc('archive',_3(N3(Zib,1),{4:1,1:1,5:1,6:1},2,6,['module'])).Ixc();h.SR(new hMh(b));g=new Fok(b.N$c().USc('Resource Adapter'),h,new kMh(this,d));g.Ay()}};_.jOb=function ALh(a,b,d){uLh();return new KLh(this,d,a,b)};_.kOb=function DLh(a,b,d){uLh();a.apc('Resource Adapter',b,(mJh(),fJh),d,QGc(nMh.prototype.n7,nMh,[this]))};_.lOb=function ELh(a,b){uLh();this.k_(mDl(a))};var J_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn',3432,Wsc);MGc(3433,1,{1:1},GLh);_.Rc=function HLh(a){return new eLh(A5(a,46))};var A_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/0methodref$ctor$Type',3433,Sib);MGc(3440,1,{1:1,13:1},KLh);_.mOb=function JLh(){};_.S_=function RLh(){return Akk(this)};_.W_=function SLh(){return Bkk(this)};_.tW=function LLh(){var a;a=new $rd;a.add(this.c.jvc(this.d.NAc('resource-adapter').QE('name',this.b.name).NE()));a.add(this.c.fvc('Resource Adapter',this.b.name,(mJh(),fJh),this.a));return a};_.FN=function MLh(){if(this.b.eOb()){return Ckk(this.b.name,this.b.get('transaction-support').asString())}return null};_.Q_=function NLh(){var a;a=new $rd;a.add(this.b.name);a.add(Wkd(this.b.bOb().name()));if(this.b.eOb()){a.add(this.b.get('transaction-support').asString())}return Akd(' ',a)};_.R_=function OLh(){var a;a=null;if(d6(this.b.bOb(),(mLh(),jLh))){a=N7c(n5c());y8c(a,Ool('archive'))}else if(d6(this.b.bOb(),(mLh(),jLh))){a=N7c(n5c());y8c(a,Ool('cubes'))}return a};_.Kl=function PLh(){return mDl(this.b.name)};_.Ll=function QLh(){return this.b.name};var B_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/1',3440,Sib);MGc(3435,1,{1:1},ULh);_.b0=function VLh(a,b){xLh(this.a,a,b)};var C_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/lambda$0$Type',3435,Sib);MGc(3436,1,{1:1},XLh);_.a0=function YLh(a){return yLh(this.a,a)};var D_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/lambda$1$Type',3436,Sib);MGc(3439,1,{1:1},$Lh);_.d0=function _Lh(a){this.a.iOb(this.c,this.d,this.b,a)};var E_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/lambda$2$Type',3439,Sib);MGc(3441,1,{1:1},bMh);_.c0=function cMh(a){return this.a.jOb(this.b,this.c,a)};var F_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/lambda$3$Type',3441,Sib);MGc(3434,1,{1:1},eMh);_.x6=function fMh(a){BLh(this.a,a)};var G_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/lambda$4$Type',3434,Sib);MGc(3437,1,{1:1,405:1},hMh);_.VU=function iMh(a){return CLh(this.a,a)};var H_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/lambda$5$Type',3437,Sib);MGc(3438,1,{1:1},kMh);_.k1=function lMh(a,b){this.a.kOb(this.b,a,b)};var I_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterColumn/lambda$6$Type',3438,Sib);MGc(6217,$wnd.Function,{1:1},nMh);_.n7=function oMh(a,b){this.a.lOb(a,b)};MGc(4828,35,{1:1,7:1,35:1},SMh);_.tOb=function RMh(){};var Q_b=bgd('org.jboss.hal.client.configuration.subsystem.resourceadapter','ResourceAdapterPreview',4828,Stc);MGc(1547,1,{1:1});_.BOb=function mOh(){var a;a=this.KOb(this.a.xA().Tvc(),this.a.xA().Rvc(),this.a.xA().Vvc(),this.a.uA().crc(),this.a.JA().TJc(),this.a.BA().RAc(),this.a.PA().$$c());this.GOb(a);return a};_.GOb=function sOh(a){};_.KOb=function wOh(a,b,d,e,g,h,i){return new wLh(a,b,d,e,g,h,i)};MGc(1551,1,{45:1,1:1});_.dk=function POh(){this.b.Ej(this.a.a.BOb())};MGc(263,1,{1:1,299:1});_.bTc=function CEl(a,b){return 'At least one of '+a+' or '+b+' is required.'};_.mWc=function OHl(a,b){return (new FKc).iu('The resource adapter is provided by ').hu(a).iu(' <code>').hu(b).iu('<\\/code>.').ju()};zTl(NJ)(53);\n//# sourceURL=hal-53.js\n")