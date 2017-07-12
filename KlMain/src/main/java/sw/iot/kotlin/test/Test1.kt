package sw.iot.kotlin.test

/**
 * Created by GoldWatch on 7/12/17.
 */
fun main(args: Array<String>) {
    println("Hello, World!")

    val list = listOf(1, 2, 3, 4, 5, 6)
    //Integer.parseInt(args[0])
    val x = if (args.size==0) null else try { args[0].toInt() } catch (e: NumberFormatException) { null }

    when (x){
        1 -> println("X == 1")
        2 -> println("X == 2")
        else -> {
            println("X[${if (args.size==0) null else args[0]}] is neither 1 nor 2")
        }
    }
}
