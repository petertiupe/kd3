import kotlinx.browser.document
import org.w3c.dom.*
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.js.*
import org.w3c.dom.css.CSS

data class Stats(val low: Int, val avg: Int, val high: Int, val color: String)

/*
@JsName("tooltipHtmlJs")
fun tooltipHtml (name: String, values: Stats) : HTMLDivElement
{
    return document.create.div {
        h4 { +name }
        table {
            tr {
                td { +"Low" }
                td { +values.low.toString() }
            }
            tr {
                td { +"Avg" }
                td { +values.avg.toString() }
            }
            tr {
                td { +"High" }
                td { +values.high.toString() }
            }
            tr {
                td { +"Variability" }
                td { +variability(values) }
            }
        }
    }
}

fun variability(state: Stats) : String
{
    return when(state.high - state.low)
    {
        in 1 .. 20 -> "Low"
        in 21 .. 50  -> "Medium"
        else -> "High"
    }
}

fun styleTable(state: Stats) = when
{
    (state.high - state.low < 33) && (state.avg < 33) -> "low" // always cold
    (state.high - state.low > 33) && (state.avg > 33) && (state.avg < 66) -> "middle"
    (state.high - state.low < 33) && (state.avg > 66) -> "high" // always hot
    else -> ""
}

fun drawTable(tableDiv: HTMLDivElement, data: dynamic)
{
    val states = js("Object.keys(data);")
    states.sort()

    val table = document.create.table{
        thead {
            tr {
                td { +"Name"}
                td { +"Low"}
                td { +"Average"}
                td { +"High"}
                td { +"Variability"}
            }
        }
    }

    for(i in 1 .. 50)
    {
        var tr = document.create.tr {
            td { +states[i].toString()}
            td { +data[states[i]].low.toString()}
            td { +data[states[i]].avg.toString()}
            td { +data[states[i]].high.toString()}
            td(classes = styleTable(data[states[i]])) { +variability(data[states[i]])}
        }

        table.appendChild(tr)
    }

    tableDiv.appendChild(table)
}
*/

fun main() {

    // JS Standard-Dokument
    document.bgColor = "FFFFFF"
    val email = document.getElementById("email") as HTMLInputElement
    email.value = "peter@tiupe.de"
    console.log("Peter wars")

    // Der Teil funktioniert, wenn man im Java-Script-Teil die D3-Bibliothek als Skript einbindet.
    // js("d3.select('.target').style('stroke-width', 8)")
    console.log("Nach dem JS-Befehl")

    d3.select("body")
        .style("background-color", "white")

    val x: Selection = d3.select(".target")
    x.style("stroke-width", 20)
    x.style("color", "red") // color: rgb(0,0,255);
    x.style("opacity", 0.2)
    console.log(x)

    val svg1 = d3.select("#dataviz_area")
    svg1.append("circle")
        .attr("cx", 2).attr("cy", 2).attr("r", 40).style("fill", "blue")
    svg1.append("circle")
        .attr("cx", 140).attr("cy", 70).attr("r", 80).style("fill", "red")
    svg1.append("circle")
        .attr("cx", 140).attr("cy", 70).attr("r", 20).style("fill", "green")

    // scale übersetzt einen numerischen Wert in eine Pixel-Position.

    val svgVizArea = d3.select("#viz_area")

// Create a scale: transform value in pixel
    val scaledX = d3.scaleLinear()
        .domain(arrayOf(0, 100))      //  .domain([0, 100])         // This is the min and the max of the data: 0 to 100 if percentages
        .range(arrayOf(20, 420))       // This is the corresponding value I want in Pixel

    val scaledY = d3.scaleLinear()
        .domain(arrayOf(0, 100))       // Prozent, siehe Oben
        .range(arrayOf(0, 250))      // Pixelwert, siehe Oben

    svgVizArea.call(d3.axisBottom(scaledX))

// Add 3 dots for 0, 50 and 100%
    svgVizArea.append("circle")
        .attr("cx", scaledX(10)).attr("cy", scaledY(30)).attr("r", 20).style("fill", "yellow")
    svgVizArea.append("circle")
        .attr("cx", scaledX(50)).attr("cy", scaledY(50)).attr("r", 20).style("fill", "black")
    svgVizArea.append("circle")
        .attr("cx", scaledX(100)).attr("cy", scaledY(100)).attr("r", 20).style("fill", "orange")

    createNewGraph("#graphArea")
    databindingExample()

}

fun createNewGraph(htmlID: String, data: Array<GraphPoint> = emptyArray()) {
    val gArea = GraphArea(xSize = 450, ySize = 400, top=10, right=40, bottom=30, left=30)

    val koordinatenSystem = d3.select(htmlID)
        .append("svg")
        .attr("width", gArea.xSize)
        .attr("height", gArea.ySize)
        // translate this svg element to leave some margin.
        .append("g")
        .attr("transform", "translate(${gArea.left},  ${gArea.top})")


    // X scale and Axis
    val scaledX = d3.scaleLinear()
        .domain(arrayOf(0, 100))         // This is the min and the max of the data: 0 to 100 if percentages
        .range(arrayOf(0, gArea.width))       // This is the corresponding value I want in Pixel

    koordinatenSystem
        .append("g")
        .attr("transform", "translate(0, ${gArea.height} )")
        .call(d3.axisBottom(scaledX))

    // Y scale and Axis
    val scaledY = d3.scaleLinear()
        .domain(arrayOf(0, 100))                // This is the min and the max of the data: 0 to 100 if percentages
        .range(arrayOf(gArea.height, 0))       // This is the corresponding value I want in Pixel

    koordinatenSystem
        .append("g")
        .call(d3.axisLeft(scaledY))

    // Add data-points
    // Add 3 dots for 0, 50 and 100%
    koordinatenSystem
        .selectAll("whatever")
        .data(data)
        .enter()
        .append("circle")
        .style("fill", "red")
        .attr("cx", {gp: GraphPoint -> scaledX(gp.x)})
        .attr("cy", {gp: GraphPoint -> scaledY(gp.y)})
        .attr("r", 7)

}

fun databindingExample() {
    val data = arrayOf(GraphPoint(10, 20), GraphPoint(40, 90), GraphPoint(80, 50))
    createNewGraph("#scatterArea", data)
}



data class GraphArea(val xSize: Int, val ySize: Int, val top: Int, val right: Int, val bottom: Int,  val left: Int) {
    val width = xSize - left - right
    val height = ySize - top - bottom
}

data class GraphPoint(val x: Int, val y: Int)



