package pe.edu.ulima.dbaccess.models.beans

class PokemonList : ArrayList<Pokemon> {
    constructor() : super()
    constructor(collection: Collection<Pokemon>) : super(collection)
}

