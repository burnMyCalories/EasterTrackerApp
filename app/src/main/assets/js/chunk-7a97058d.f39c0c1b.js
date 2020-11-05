(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7a97058d"],{"0d8f":function(e,t,r){"use strict";r("43a3")},"43a3":function(e,t,r){},"73cf":function(e,t,r){"use strict";r.r(t);var a=r("7a23"),n=Object(a["F"])("data-v-9720e4aa");Object(a["s"])("data-v-9720e4aa");var i={id:"register",class:"my-container"},s=Object(a["g"])("h1",{class:"title"},"Register",-1),o={action:""},l={class:"form-group"},c=Object(a["g"])("label",{for:"ipt_reg_name"},"User Name",-1),u=Object(a["g"])("small",{class:"tips"},"Only 4 to 32 letters, numbers and underscores are allowed",-1),d={class:"form-group"},p=Object(a["g"])("label",{for:"ipt_reg_pwd"},"Password",-1),m=Object(a["g"])("small",{class:"tips"},"Your password should be no less than 6 and no more than 32 characters",-1),f={class:"form-group"},b=Object(a["g"])("label",{for:"ipt_reg_cpwd"},"Confirm Password",-1),g=Object(a["g"])("small",{class:"tips"},"Two inputed password should be the same",-1),v={class:"form-group"},h={key:0,for:"ipt_reg_email"},w=Object(a["g"])("small",{class:"tips"},"Your email will only be used for password retrieval",-1),y={class:"d-flex"},j={class:"form-group"},O=Object(a["g"])("label",{for:"ipt_reg_vcode"},"Validation Code",-1),_={class:"btngrp"},C=Object(a["g"])("button",{class:"btn btn-link"},"Cancel",-1);Object(a["q"])();var V=n((function(e,t,r,V,E,k){var A=Object(a["w"])("router-link");return Object(a["p"])(),Object(a["d"])("div",i,[s,Object(a["g"])("form",o,[Object(a["g"])("div",l,[c,u,Object(a["E"])(Object(a["g"])("input",{type:"text",class:"form-control",id:"ipt_reg_name",name:"user",minlength:"4",maxlength:"32","onUpdate:modelValue":t[1]||(t[1]=function(e){return E.username=e}),onBlur:t[2]||(t[2]=function(e){return k.verifyName()})},null,544),[[a["B"],E.username]])]),Object(a["g"])("div",d,[p,m,Object(a["E"])(Object(a["g"])("input",{type:"password",class:"form-control",id:"ipt_reg_pwd",name:"pwd",autocomplete:"new-password","onUpdate:modelValue":t[3]||(t[3]=function(e){return E.password=e}),onBlur:t[4]||(t[4]=function(e){return k.verifyPwd()})},null,544),[[a["B"],E.password]])]),Object(a["g"])("div",f,[b,g,Object(a["E"])(Object(a["g"])("input",{type:"password",class:"form-control",id:"ipt_reg_cpwd",name:"cpwd",autocomplete:"new-password","onUpdate:modelValue":t[5]||(t[5]=function(e){return E.cpassword=e}),onBlur:t[6]||(t[6]=function(e){return k.verifyCPwd()})},null,544),[[a["B"],E.cpassword]])]),Object(a["g"])("div",v,[E.contactIsEmail?(Object(a["p"])(),Object(a["d"])("label",h,"Email")):Object(a["e"])("",!0),w,Object(a["g"])("div",y,[E.contactIsEmail?Object(a["E"])((Object(a["p"])(),Object(a["d"])("input",{key:0,type:"email",class:"form-control",id:"ipt_reg_email","onUpdate:modelValue":t[7]||(t[7]=function(e){return E.email=e}),onBlur:t[8]||(t[8]=function(e){return k.verifyEmail()}),onChange:t[9]||(t[9]=function(e){return k.emailChange()})},null,544)),[[a["B"],E.email]]):Object(a["e"])("",!0),Object(a["g"])("button",{class:"btn btn-link",type:"button",id:"verifyButton",onClick:t[10]||(t[10]=function(e){return k.getVCode()})},Object(a["y"])(E.verifyLabel),1)])]),Object(a["g"])("div",j,[O,Object(a["E"])(Object(a["g"])("input",{type:"text",class:"form-control",id:"ipt_reg_vcode","onUpdate:modelValue":t[11]||(t[11]=function(e){return E.validation=e}),onBlur:t[12]||(t[12]=function(e){return k.verifyCode()})},null,544),[[a["B"],E.validation]])]),Object(a["g"])("div",_,[Object(a["g"])(A,{to:"/"},{default:n((function(){return[C]})),_:1}),Object(a["g"])("button",{class:"btn btn-primary",type:"button",onClick:t[13]||(t[13]=function(e){return k.register()})},"Submit")])])])})),E=(r("a15b"),{data:function(){return{username:"",password:"",cpassword:"",gender:null,nickname:null,email:"",contactIsEmail:!0,verifyLabel:"Verify",trueVCode:null,validation:"",resVCodeEmail:"",latitude:0,longitude:0}},methods:{getVCode:function(){var e=this,t=document.querySelector("#verifyButton");t.blur(),t.setAttribute("disabled","disabled");var r=60,a=setInterval((function(){r>0?e.verifyLabel="".concat(r--," s"):(clearInterval(a),e.verifyLabel="Verify",t.removeAttribute("disabled"))}),1e3);this.axios.post("/confirm",null,{params:{email:e.email}}).then((function(t){e.trueVCode=t.data.code,e.resVCodeEmail=e.email}))},updateInput:function(e,t){var r=document.querySelector(e),a=r.classList;return a.remove(t?"is-invalid":"is-valid"),a.add(t?"is-valid":"is-invalid"),t},verifyName:function(){var e=/^[\w]{4,32}$/;return this.updateInput("#ipt_reg_name",e.test(this.username))},verifyPwd:function(){var e=/^.{6,32}$/;return this.updateInput("#ipt_reg_pwd",e.test(this.password))},verifyCPwd:function(){var e=/^.{6,32}$/;return this.updateInput("#ipt_reg_cpwd",e.test(this.cpassword)&&this.password===this.cpassword)},emailChange:function(){""!==this.resVCodeEmail&&this.verifyCode()},verifyEmail:function(){var e=/^[A-Za-z0-9]+([_.][A-Za-z0-9]+)*@([A-Za-z0-9-]+\.)+[A-Za-z]{2,6}$/;return this.updateInput("#ipt_reg_email",e.test(this.email))},verifyCode:function(){return this.updateInput("#ipt_reg_vcode",this.trueVCode===this.validation&&this.email===this.resVCodeEmail)},verifyAll:function(){var e=[];if(!1===this.verifyName()&&e.push("user name"),!1===this.verifyPwd()&&e.push("password"),!1===this.verifyCPwd()&&e.push("confirm password"),!1===this.verifyEmail()&&e.push("email"),!1===this.verifyCode()&&e.push("validation code"),0!==e.length){var t="Please check your ".concat(e.join(", "),"!");return this.$store.commit("updateAlert",{msg:t,type:"danger",sync:!1}),!1}return!0},register:function(){var e=this;if(!1!==this.verifyAll()){var t=this;this.$store.commit("updateAlert",{msg:"Registering, please for a moment...",type:"primary",sync:!0}),this.axios.post("/register",null,{params:{username:t.username,password:t.password,contact:t.email,gender:"",nickname:"",latitude:t.latitude,longitude:t.longitude}}).then((function(r){var a=r.data.result.data[0];a.password=e.password,e.$store.commit("login",a),t.axios.put("/login",null,{params:a}).then((function(r){t.$store.commit("updateAlert",{msg:"Successfully registered!",type:"success",sync:!1}),e.$router.push("/addProfile")}))})).catch((function(r){500===r.response.status&&(e.updateInput("#ipt_reg_name",!1),t.$store.commit("updateAlert",{msg:"Repeated user name '".concat(t.username,"' founded! Please try a new one!"),type:"danger"}))}))}}}});r("0d8f");E.render=V,E.__scopeId="data-v-9720e4aa";t["default"]=E},a15b:function(e,t,r){"use strict";var a=r("23e7"),n=r("44ad"),i=r("fc6a"),s=r("a640"),o=[].join,l=n!=Object,c=s("join",",");a({target:"Array",proto:!0,forced:l||!c},{join:function(e){return o.call(i(this),void 0===e?",":e)}})},a640:function(e,t,r){"use strict";var a=r("d039");e.exports=function(e,t){var r=[][e];return!!r&&a((function(){r.call(null,t||function(){throw 1},1)}))}}}]);
//# sourceMappingURL=chunk-7a97058d.f39c0c1b.js.map