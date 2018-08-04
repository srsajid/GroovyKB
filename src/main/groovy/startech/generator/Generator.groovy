package startech.generator

import startech.updater.LocalDatabaseUpdater
import util.DB

class Generator {
    public static void main(String[] args) {
        DB db = new DB("startech")
        new LocalDatabaseUpdater().updateDatabase(db, LocalDatabaseUpdater.advance)
        new FacebookFeedGenerator().generate(db)
        new SiteMapGenerator(db).generate()
    }
}
