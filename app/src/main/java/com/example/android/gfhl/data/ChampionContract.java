package com.example.android.gfhl.data;

import android.provider.BaseColumns;

public final class ChampionContract {

    private ChampionContract() {
    }


    public static class ChampionEntry implements BaseColumns {

        public static final String TABLE_NAME = "champions";
        public static final String COLUMN_NAME_CHAMPION_ICON = "icon";
        public static final String COLUMN_NAME_CHAMPION_IMAGE = "image";
        public static final String COLUMN_NAME_CHAMPION_NAME = "name";
        public static final String COLUMN_NAME_CHAMPION_TITLE = "title";
        public static final String COLUMN_NAME_CHAMPION_HP = "hp";
        public static final String COLUMN_NAME_CHAMPION_MANA = "mana";
        public static final String COLUMN_NAME_CHAMPION_ARMOR = "armor";
        public static final String COLUMN_NAME_CHAMPION_MR = "mr";
        public static final String COLUMN_NAME_CHAMPION_DMG = "dmg";
    }
}
