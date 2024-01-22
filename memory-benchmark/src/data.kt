import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path

internal typealias CategoryToValue = Map<String, Double>
internal typealias SizeToOperationData = Map<String, Map<String, CategoryToValue>>

@OptIn(ExperimentalSerializationApi::class)
internal val json = Json {
  ignoreUnknownKeys = true
  prettyPrint = true
  prettyPrintIndent = "  "
}

@Suppress("unused")
@Serializable
internal class ChartData(
  val sizes: Collection<String>,
  // group to map of operation to map of category to value
  val groups: Map<String, SizeToOperationData>,
)

fun writeData(name: String, sizes: Collection<String>,  groups: Map<String, SizeToOperationData>) {
  Files.writeString(Path.of("site", "components/$name.ts"), "export const chartData = " + json.encodeToString<ChartData>(ChartData(sizes = sizes, groups = groups)))
}