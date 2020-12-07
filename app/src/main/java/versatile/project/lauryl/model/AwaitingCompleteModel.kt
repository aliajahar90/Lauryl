package versatile.project.lauryl.model

class AwaitingCompleteModel(
    var orderIdVal: String,
    var date: String,
    var time: String,
    var pickUpAddress: String,
    var orderStage: String,
    var dataItem: MyOrdersDataItem
) {
}