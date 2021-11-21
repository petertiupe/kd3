@JsModule("d3")
@JsNonModule
//external fun <T> sorted(a: Array<T>): Boolean
external object d3 {
     fun select(selector: dynamic): Selection
     fun scaleLinear(): dynamic
     fun axisBottom(fkt: dynamic) : dynamic
     fun axisLeft(fkt: dynamic): dynamic
}

external class Selection() {
     fun style(name: String, value: dynamic = definedExternally,  priority: dynamic = definedExternally) : Selection
     fun append(name: String) : Selection
     fun attr(name: String, value: dynamic = definedExternally): Selection
     fun call(fkt: dynamic): Selection
     fun selectAll(id: String): Selection
     fun data(dt: dynamic): Selection
     fun enter(): Selection


}
