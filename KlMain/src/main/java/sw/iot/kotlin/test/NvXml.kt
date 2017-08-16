package sw.iot.kotlin.test

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

fun getNodeSize(node: Node, nodes: NodeList): Int {
    var len = 0
    var childs = node.childNodes
    //println(childs.length)
    for (j in 0..childs.length-1) {
        var attrs = childs.item(j).attributes
        if (attrs==null) continue

        var type = 0;
        var size = 0;
        for (k in 0..attrs.length-1) {
            if (attrs.item(k).nodeName=="type") {
                var attr = attrs.item(k).nodeValue
                if (attr.length > "uint16".length)  {
                    type = getTypeSize(attr, nodes)
                } else {
                    type = attr.substring(attr.indexOf('t') + 1).toInt()
                }
            }
            if (attrs.item(k).nodeName=="sizeOf") {
                var attr = attrs.item(k).nodeValue
                size = attr.toInt()
            }
        }
        len += type * size
    }

    return len
}

val typeMap = hashMapOf<String, Int>()

fun getTypeSize(type: String, nodes: NodeList): Int {
    var size: Int? = typeMap.get(type)
    if (size!=null) return size;

    var node = nodes.item(0)
    for (i in 0..nodes.length - 1) {
        var attrs = nodes.item(i).attributes
        for (j in 0..attrs.length - 1) {
            if (attrs.item(j).nodeValue == type) {
                node = nodes.item(i)
                break;
            }
        }
    }

    size = getNodeSize(node, nodes)
    typeMap.put(type, size)

    return size
}

fun main(args: Array<String>) {
    var start = System.currentTimeMillis()
    try {
        var f: File = File("NvDefinition.xml")
        var factory = DocumentBuilderFactory.newInstance()
        var builder = factory.newDocumentBuilder()
        var doc = builder.parse(f)
        var types = doc.getElementsByTagName("DataType")
        var nodes = doc.getElementsByTagName("NvItem")
        println("NvItem total "+nodes.length)
        for (i in 0..nodes.length-1) {
            var attrs = nodes.item(i).attributes
            for (j in 0..attrs.length-1) {
                if (attrs.item(j).nodeName=="id")
                    print(attrs.item(j).nodeValue)
            }

            var len = getNodeSize(nodes.item(i), types)
            println(" " + len)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    var end = System.currentTimeMillis()
    println("Elapse=" + (end-start))
}
