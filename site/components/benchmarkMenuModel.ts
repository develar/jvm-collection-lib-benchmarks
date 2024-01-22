export type CategoryToValue = { [key: string]: number }
export type SizeToOperationData = { [key: string]: CategoryToValue }

export interface ChartData {
  readonly sizes: Array<string>
  readonly groups: { [key: string]: SizeToOperationData }
}

export const tabs = [
  {
    label: "Object",
    path: "/object-to-object",
    type: "ObjectToObject",
  },
  {
    label: "String",
    path: "/string",
    type: "String",
  },
  {
    label: "Ordered Object",
    path: "/ordered-object-to-object",
    type: "LinkedMap",
  },
  {
    label: "Ref to Object",
    path: "/ref-to-object",
    type: "ReferenceToObject",
  },
  {
    label: "Int",
    path: "/int-to-int",
    type: "IntToInt",
  },
  {
    label: "Int to Object",
    path: "/int-to-object",
    type: "IntToObject",
  },
  {
    label: "Object to Int",
    path: "/object-to-int",
    type: "ObjectToInt",
  }
]