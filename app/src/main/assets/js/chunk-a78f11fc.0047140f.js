(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-a78f11fc"],{"1c3d":function(t,n,e){"use strict";e.r(n);var c=e("7a23"),i=Object(c["F"])("data-v-1dd9a18e");Object(c["s"])("data-v-1dd9a18e");var o={key:0,id:"myProfile",class:"my-container"},s={class:"info-box"},r={class:"potrait"},a={key:0,class:"fas fa-mars fa-lg"},u={key:1,class:"fas fa-venus fa-lg"},l={key:2,class:"fas fa-question fa-lg"},b={class:"nickname"},d=Object(c["g"])("hr",null,null,-1);Object(c["q"])();var f=i((function(t,n,e,i,f,g){return f.user?(Object(c["p"])(),Object(c["d"])("div",o,[Object(c["g"])("div",s,[Object(c["g"])("div",r,[Object(c["g"])("img",{class:"img img-thumbnail rounded-circle",src:f.imgsrc,alt:"",id:"myPortraitImg"},null,8,["src"]),Object(c["g"])("span",{class:["gender",f.user.gender]},["M"===f.user.gender?(Object(c["p"])(),Object(c["d"])("i",a)):"F"===f.user.gender?(Object(c["p"])(),Object(c["d"])("i",u)):(Object(c["p"])(),Object(c["d"])("i",l))],2)]),Object(c["g"])("div",b,Object(c["y"])(f.user.nickname),1)]),d,Object(c["g"])("button",{onClick:n[1]||(n[1]=function(){return g.logout.apply(g,arguments)}),class:"btn btn-outline-primary btn-block"},"Add Friend"),Object(c["g"])("button",{onClick:n[2]||(n[2]=function(t){return g.editProfile()}),class:"btn btn-outline-primary btn-block"},"Edit Profile"),Object(c["g"])("button",{onClick:n[3]||(n[3]=function(){return g.logout.apply(g,arguments)}),class:"btn btn-outline-primary btn-block"},"Change Password"),Object(c["g"])("button",{onClick:n[4]||(n[4]=function(){return g.logout.apply(g,arguments)}),class:"btn btn-outline-primary btn-block"},"About"),Object(c["g"])("button",{onClick:n[5]||(n[5]=function(){return g.logout.apply(g,arguments)}),class:"btn btn-link btn-block mt-5"},"Log Out")])):Object(c["e"])("",!0)})),g=(e("99af"),{data:function(){return{user:null,imgsrc:e("b194")}},setup:function(){return{}},mounted:function(){var t=this,n=this;this.user=this.$store.state.currentUser,this.axios.get("/files/".concat(this.user.id,"_portrait.jpg")).then((function(e){n.imgsrc="".concat("http://104.168.190.12:8080/EasterTracker","/files/").concat(t.user.id,"_portrait.jpg")})).catch((function(t){console.log(t)}))},methods:{logout:function(){this.axios.put("/logout",null,{params:{username:this.user.username}}),this.$store.commit("logout"),this.$router.push("/")},loadProfile:function(){},img:function(){console.log("dsf"),this.imgsrc=e("b194")},editProfile:function(){this.$store.commit("editProfile"),this.$router.push("/addProfile")}}});e("a5db");g.render=f,g.__scopeId="data-v-1dd9a18e";n["default"]=g},"9b3a":function(t,n,e){},a5db:function(t,n,e){"use strict";e("9b3a")},b194:function(t,n,e){t.exports=e.p+"img/default-portrait.32fdb4f1.svg"}}]);
//# sourceMappingURL=chunk-a78f11fc.0047140f.js.map