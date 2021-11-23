<template>
  <el-container>
    <el-main>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="URL:">
          <el-row :gutter="20">
            <el-col :span="16">
              <el-input
                v-model="form.url"
                placeholder="请输入URL地址(以http或https开头)"
              >
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="onSubmit">获取短URL</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="Tiny:" v-if="form.tiny != '' ">
          <el-row :gutter="20">
            <el-col :span="16">
              <el-input
                v-model="form.tiny" readonly
              >
              </el-input>
            </el-col>
            <el-col :span="4">
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="Error:" v-if="form.err != '' ">
          <el-row :gutter="20">
            <el-col :span="16">
              <el-input
                v-model="form.err" readonly
              >
              </el-input>
            </el-col>
            <el-col :span="4">
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>
<script>
export default {
  data () {
    return {
      form: {
        url: '',
        tiny: '',
        err: ''
      }
    }
  },
  methods: {
    onSubmit () {
      let baseUrl = window.location.href
      if (baseUrl.endsWith('/')) {
        baseUrl = baseUrl.substring(0, baseUrl.length - 1)
      }
      baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf('/'))
      console.log(baseUrl)
      let url = baseUrl + '/tiny'
      let params = {
        url: this.form.url
      }
      this.form.tiny = ''
      this.form.err = ''
      this.$http
        .post(url, params)
        .then((response) => {
          this.form.tiny = baseUrl + '/t/' + response.data.data
        })
        .catch(function (response) {
          this.form.err = response.data.msg
        })
    }
  }
}
</script>
