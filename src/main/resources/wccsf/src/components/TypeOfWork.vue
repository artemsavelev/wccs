<template>
    <div>
        <div class="main font-s mt-10">
            <div class="font-s mt-1">
                Виды работ:
            </div>

            <ModalWin v-bind:typeSection="typeSection"/>

        </div>
    </div>
</template>

<script>
    import ModalWin from "./ModalWin";

    export default {
        name: "TypeOfWork",
        components: { ModalWin },
        data() {
            return {
                typeSection: 0

            }
        }
    }
</script>

<style scoped>

</style>