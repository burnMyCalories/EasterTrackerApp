(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-215a129a"],{bea2:function(t,e,n){},d09b:function(t,e,n){"use strict";n("bea2")},eec5:function(t,e,n){"use strict";n.r(e);var o=n("7a23"),s={id:"welcome",class:"my-container"},r=Object(o["h"])("h1",{class:"title lg"},"Welcome",-1),c={id:"form-containter"},a={class:"loginForm",action:""},i={class:"form-group"},l=Object(o["h"])("label",{for:"ipt_login_name"},"Username or Email address",-1),u={class:"form-group"},g=Object(o["h"])("label",{for:"ipt_login_pwd"},"Password",-1),m={href:"javascript:",class:"ml-3"},p={class:"btn-grp"},d=Object(o["h"])("hr",null,null,-1);function b(t,e,n,b,h,f){return Object(o["q"])(),Object(o["d"])("div",s,[r,Object(o["h"])("div",c,[Object(o["h"])("form",a,[Object(o["h"])("div",i,[l,Object(o["F"])(Object(o["h"])("input",{type:"text",class:"form-control",id:"ipt_login_name","onUpdate:modelValue":e[1]||(e[1]=function(t){return h.username=t}),autocomplete:"username"},null,512),[[o["C"],h.username]])]),Object(o["h"])("div",u,[g,Object(o["h"])("a",m,[Object(o["h"])("small",{onClick:e[2]||(e[2]=function(t){return f.forgot()})},"Forgot?")]),Object(o["F"])(Object(o["h"])("input",{type:"password",class:"form-control",id:"ipt_login_pwd","onUpdate:modelValue":e[3]||(e[3]=function(t){return h.password=t}),autocomplete:"current-password"},null,512),[[o["C"],h.password]])])]),Object(o["h"])("div",p,[Object(o["h"])("button",{onClick:e[4]||(e[4]=function(t){return f.login()}),class:"btn btn-primary btn-lg btn-block"},"Login"),d,Object(o["h"])("button",{class:"btn btn-secondary btn-lg btn-block",onClick:e[5]||(e[5]=function(t){return f.register()})},"Register")])])])}n("d3b7");var h={components:{},setup:function(){},data:function(){return{username:"",password:"",logging:!1,message:""}},provide:function(){},methods:{login:function(){var t=this,e=this;this.logging||(this.logging=!0,this.$store.commit("updateAlert",{msg:"Please wait for a monment...",type:"primary",sync:!0}),this.axios.put("/login",null,{params:{username:this.username,password:this.password}}).then((function(t){var n=t.data.result.data[0];console.log(n),n.password=e.password,e.$store.commit("login",n),e.$store.commit("updateAlert",{msg:"Welcome ".concat(n.nickname?n.nickname:n.username,"!"),type:"success"}),e.$router.push("/home")})).catch((function(e){console.log(e);var n=e.response.data.status.msg;t.$store.commit("updateAlert",{msg:n,type:"danger",sync:!1})})).finally((function(){e.logging=!1})))},register:function(){this.logging||this.$router.push("/register")},forgot:function(){this.logging||console.log("forgot")}}};n("d09b");h.render=b;e["default"]=h}}]);
//# sourceMappingURL=chunk-215a129a.d923a99b.js.map