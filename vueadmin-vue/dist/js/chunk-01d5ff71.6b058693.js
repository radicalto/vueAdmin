(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-01d5ff71"],{"1ad9":function(s,e,r){},6494:function(s,e,r){"use strict";r.r(e);var t=function(){var s=this,e=s.$createElement,r=s._self._c||e;return r("div",{staticStyle:{"text-align":"center"}},[r("h2",[s._v("你好！"+s._s(s.userInfo.username)+" 同学")]),r("el-form",{ref:"passForm",attrs:{model:s.passForm,"status-icon":"",rules:s.rules,"label-width":"100px"}},[r("el-form-item",{attrs:{label:"旧密码",prop:"currentPass"}},[r("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:s.passForm.currentPass,callback:function(e){s.$set(s.passForm,"currentPass",e)},expression:"passForm.currentPass"}})],1),r("el-form-item",{attrs:{label:"新密码",prop:"password"}},[r("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:s.passForm.password,callback:function(e){s.$set(s.passForm,"password",e)},expression:"passForm.password"}})],1),r("el-form-item",{attrs:{label:"确认密码",prop:"checkPass"}},[r("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:s.passForm.checkPass,callback:function(e){s.$set(s.passForm,"checkPass",e)},expression:"passForm.checkPass"}})],1),r("el-form-item",[r("el-button",{attrs:{type:"primary"},on:{click:function(e){return s.submitForm("passForm")}}},[s._v("提交")]),r("el-button",{on:{click:function(e){return s.resetForm("passForm")}}},[s._v("重置")])],1)],1)],1)},a=[],o={name:"Login",data:function(){var s=this,e=function(e,r,t){""===r?t(new Error("请再次输入密码")):r!==s.passForm.password?t(new Error("两次输入密码不一致!")):t()};return{userInfo:{},passForm:{password:"",checkPass:"",currentPass:""},rules:{password:[{required:!0,message:"请输入新密码",trigger:"blur"},{min:6,max:12,message:"长度在 6 到 12 个字符",trigger:"blur"}],checkPass:[{required:!0,validator:e,trigger:"blur"}],currentPass:[{required:!0,message:"请输入当前密码",trigger:"blur"}]}}},created:function(){this.getUserInfo()},methods:{getUserInfo:function(){var s=this;this.$axios.get("/sys/userInfo").then((function(e){s.userInfo=e.data.data}))},submitForm:function(s){var e=this;this.$refs[s].validate((function(r){if(!r)return console.log("error submit!!"),!1;var t=e;e.$axios.post("/sys/user/updatePass",e.passForm).then((function(r){t.$alert(r.data.msg,"提示",{confirmButtonText:"确定",callback:function(r){e.$refs[s].resetFields()}})}))}))},resetForm:function(s){this.$refs[s].resetFields()}}},n=o,u=(r("9d2c"),r("2877")),i=Object(u["a"])(n,t,a,!1,null,"5f4e465a",null);e["default"]=i.exports},"9d2c":function(s,e,r){"use strict";r("1ad9")}}]);
//# sourceMappingURL=chunk-01d5ff71.6b058693.js.map