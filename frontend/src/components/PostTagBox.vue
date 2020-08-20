<template>
  <v-row>
    <v-col cols="12">
		<v-chip class="mx-1" v-for="tag in tags" :key="tag">
			{{ tag }}
		</v-chip>
    </v-col>
  </v-row>
</template>

<script>
  export default {
		name: "PostTagBox",
    data() {
			return {
				tags: this.$attrs.tagList,
			}
		}
  }
</script>

<style>

</style>