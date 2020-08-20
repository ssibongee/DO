<template>
  <div>
    <p>작성자: {{ child.author }} | 내용: {{ child.content }}</p>
  </div>
</template>

<script>
export default {
    name: 'ChildComment',
    data() {
      return {
        child: this.$attrs.child
      }
    }
}
</script>

<style>

</style>