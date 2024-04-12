package com.snick55.avitotestapp.data

interface Loader<T>{

   suspend fun load(pageIndex: Int,pageSize: Int):List<T>


}