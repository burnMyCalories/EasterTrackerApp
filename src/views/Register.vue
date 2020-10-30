<template>
  <div id="register" class="my-container">
    <h1 class="title">Register</h1>
    <form action="">
      <div class="form-group">
        <label for="ipt_reg_name">Username</label>
        <input type="text" class="form-control" id="ipt_reg_name">
      </div>
      <div class="form-group">
        <label for="ipt_reg_pwd">Password</label>
        <input type="password" class="form-control" id="ipt_reg_pwd">
      </div>
      <div class="form-group">
        <label for="ipt_reg_cpwd">Confirm Password</label>
        <input type="password" class="form-control" id="ipt_reg_cpwd">
      </div>
      <div class="form-group">
        <label for="ipt_reg_email">Email</label>
        <div style="display:flex;">
          <input type="email" class="form-control" id="ipt_reg_email">
          <button class="btn btn-link">Verify</button>
        </div>
      </div>
      <div class="form-group">
        <label for="ipt_reg_cpwd">Validation Code</label>
        <input type="tel" class="form-control" id="ipt_reg_cpwd">
      </div>
      <div class="btngrp">
        <button class="btn btn-primary">Submit</button>
        <router-link to="/">Cancel</router-link>
      </div>
    </form>

  </div>
</template>
<style scoped>
.btngrp {
  display: flex;
  justify-content: space-between;
}
</style>
