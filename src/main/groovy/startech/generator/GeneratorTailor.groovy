package startech.generator

import startech.updater.LocalDatabaseUpdater
import tailor.URL
import util.DB

class GeneratorTailor {
    public static void main(String[] args) {
        String database = args.length > 0 ? args[0] : "pqs"
        String host = args.length > 1 ? args[1] : "www.pqs.com.bd"
        DB db = new DB(database)
        new LocalDatabaseUpdater().updateDatabase(db, LocalDatabaseUpdater.advance, host)
        new FacebookFeedGenerator().generate(db)
        new SiteMapGenerator(db, host, new URL(db, host)).generate()
    }
}
