<template>
  <div>

    <AddUser/>

    <AddComponent/>

    <ListComponents/>

  </div>
</template>

<script>
import AddUser from "@/components/AddUser";
import AddComponent from "@/components/AddComponent";
import ListComponents from "@/components/ListComponents";

export default {
  name: "Admin",
  components: {ListComponents, AddComponent, AddUser},
  data () {
    return {
    }
  },
}
</script>

<style lang="scss">

</style>