
fun main() {
   val stringListA = listOf(1,2,3,4,null)
    val stringListContainer = mutableListOf<Int>()
    //  stringListA.mapNotNullTo(stringListContainer){ it?.times(2)}
    // de-duplicate
    val listAIterator = stringListA.iterator()
    while (listAIterator.hasNext()){
        val element = listAIterator.next()
        element?.times(2)?.let {
            stringListContainer.add(it)
        }
    }

    println(stringListContainer)
}






