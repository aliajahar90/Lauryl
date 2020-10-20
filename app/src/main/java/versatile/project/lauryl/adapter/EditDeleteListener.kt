package versatile.project.lauryl.adapter

interface EditDeleteListener {
 fun editClicked(position:Int)
 fun deleteClicked(id: String, position: Int)
}
