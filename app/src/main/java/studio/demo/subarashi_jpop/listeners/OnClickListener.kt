package studio.demo.subarashi_jpop.listeners

interface OnClickListener {
    fun ViewInfoOnClick(model: Any){}
    fun AddToFavouriteOnClick(model: Any){}
    fun deleteFromFavouriteOnClick(name: String){}
}