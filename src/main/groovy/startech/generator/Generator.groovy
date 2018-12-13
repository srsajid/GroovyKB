package startech.generator

import startech.updater.LocalDatabaseUpdater
import util.DB

class Generator {
    public static void main(String[] args) {
        String database = args.length > 0 ? args[0] : "startech"
        String host = args.length > 1 ? args[1] : null
        DB db = new DB(database)
        new LocalDatabaseUpdater().updateDatabase(db, LocalDatabaseUpdater.advance, host)
        new FacebookFeedGenerator().generate(db)
        new SiteMapGenerator(db, host).generate()
    }
}
