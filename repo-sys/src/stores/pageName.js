import { defineStore } from "pinia";
import { ref } from "vue";
// 用于传递当前页面的名字，给面包屑作参考

export const usePageNameStore = defineStore('pageName',()=>{

    const pageName = ref('')

    const setPage = (newPage)=>{
        pageName.value = newPage
    }

    const removePage = ()=>{
        pageName.value = ''
    }
    return {
        pageName,setPage,removePage
    }
},{persist:true}
)