/**
 * We declare a package-level function main which returns Unit and takes
 * an Array of strings as a parameter. Note that semicolons are optional.
 */

fun main(args: Array<String>) {
    
    println("====================GETTER & SETTER====================")
    val myText = MyText()
    
    println("The privious value ${myText.myVariable}")
    myText.myVariable = -10
    println("The value will change ${ myText.myVariable }")
    myText.myVariable = 10
    println("The value won't change ${ myText.myVariable }")
    println("You also got a second var ${ myText.mySecondVariable }")
    
    println("======================EXTENSION=========================")
    
    println("YOU GOT AN EXTENSION FUNCTION: ${myText.foo()}")
    
    println("======================DATA CLASS=========================")
    
    val user = User("Shawn",28)
    println("New User Created: ${user.toString()}")
    val olderUser = user.copy(age = user.age + 1)
    println("Older User has one year older age: $olderUser") //"User(name=Shawn, age=29)"
    
    //Desturcturing Declarations
    val (name, age) = user
    println("$name, $age years of age") // prints "Shawn, 28 years of age"
    
    println("======================SEALED CLASS=========================")
    val userMoving = UserMoving(user)
    println(userMoving.getCurrentLocation()) // (0,0)
    userMoving.move(CompassDirection.North)
    userMoving.move(CompassDirection.North)
    userMoving.move(CompassDirection.South)
    userMoving.move(CompassDirection.East)
    userMoving.move(CompassDirection.East)
    userMoving.move(CompassDirection.West)
    println(userMoving.getCurrentLocation()) // (1,1)
    userMoving.move(CompassDirection.JumpToLocation(10,10))
    println(userMoving.getCurrentLocation()) // (10,10)
    
    println("======================ENUM CLASS=========================")
    println("${Direction.NORTH.value}")
    println(Direction.EAST.getCustomizedString())  
    
    
    println("======================CLASS DELEGATION===================")
    val topLevelClass = TopLevelClass("Shawn")
    topLevelClass.print()
    DerivedClass(TopLevelClass("New Shawn")).print()
    
    println("======================Lazy===================")
    println(lazyValue)
    println(lazyValue)
    
    println("======================Infix function===================")
    println(userMoving.getCurrentLocation()) // (10,10)
    userMoving move CompassDirection.North
    println(userMoving.getCurrentLocation()) // (11,10)
    
    println("===============Operation Overloading===================")
    var point = Point(10, 20)
    point++
    println(point) // Point(11,21)

    
}

data class Point(var x: Int, var y: Int)

operator fun Point.inc() = Point(x+1,y+1)


val lazyValue:String by lazy(:: lazyValueLambda)

fun lazyValueLambda():String {
    println("Computed the lazy variable")
    return "Hello wa"
}

interface Base {
    fun print()
}

class TopLevelClass(val name:String) : Base {
    
    override fun print() {
        println("My name is $name")
    }
}

class DerivedClass(b: Base) : Base by b

enum class Direction(val value: Int) {
    
    NORTH(0),
    SOUTH(1),
    WEST(2),
    EAST(3);
    
    fun getCustomizedString() : String {
        when(this) {
            NORTH -> return "${this.value}<->North"
            SOUTH -> return "${this.value}<->South"
            WEST -> return "${this.value}<->West"
            EAST -> return "${this.value}<->East"
        }
    }
    
}

sealed class CompassDirection {
    object North: CompassDirection()
    object South: CompassDirection()
    object West: CompassDirection()
    object East: CompassDirection()
    class JumpToLocation(val xValue: Int, val yVaule: Int): CompassDirection()
}

class UserMoving(val user:User) {
    
    private var currentLocationX = 0
    private var currentLocationY = 0
    
    fun getCurrentLocation() : String {
        return "$user locates in (${this.currentLocationX},${this.currentLocationY})"
    }
    
    infix fun move(direction:CompassDirection) = when(direction) {
        CompassDirection.North -> {
            println("User move towards North --> X + 1 ")
            this.currentLocationX += 1
        }
        CompassDirection.South -> {
            println("User move towards South --> X - 1 ")
            this.currentLocationX -= 1
        }
        CompassDirection.West -> {
            println("User move towards West --> Y - 1 ")
            this.currentLocationY -= 1
        }
        CompassDirection.East -> {
            println("User move towards East --> Y + 1 ")
            this.currentLocationY += 1
        }
        is CompassDirection.JumpToLocation -> {
            println("User Jumped!!!")
            this.currentLocationY = direction.yVaule
            this.currentLocationX = direction.xValue
        }
        
    }
    
}

data class User(val name:String, val age: Int)

class MyText() {
    
    var mySecondVariable: Int
    
    init {
        this.mySecondVariable = 2
    }
    
    var myVariable: Int = 1
        set(value){
            if (field >= value) {
                field = value 
            }
        }
    
    constructor(number:Int):this() {
        this.mySecondVariable = number
    }
    
}

fun MyText.foo() = "RETURNING Your Second Variable: ${this.mySecondVariable}"
