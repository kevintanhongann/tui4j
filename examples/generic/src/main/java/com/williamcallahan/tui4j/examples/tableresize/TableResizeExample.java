package com.williamcallahan.tui4j.examples.tableresize;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Renderer;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.border.StandardBorder;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.table.StyleFunc;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.table.Table;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.WindowSizeMessage;

public class TableResizeExample implements Model {

    private Table table;

    public TableResizeExample() {
        Renderer re = Renderer.defaultRenderer();
        Style baseStyle = re.newStyle().paddingLeft(1);
        Style headerStyle = baseStyle.foreground(Color.color("252")).bold(true);
        Style selectedStyle = baseStyle.foreground(Color.color("#01BE85")).background(Color.color("#00432F"));

        String[][] typeColors = {
                {"Bug", "#D7FF87"},
                {"Electric", "#FDFF90"},
                {"Fire", "#FF7698"},
                {"Flying", "#FF87D7"},
                {"Grass", "#75FBAB"},
                {"Ground", "#FF875F"},
                {"Normal", "#929292"},
                {"Poison", "#7D5AFC"},
                {"Water", "#00E2C7"}
        };

        String[][] dimTypeColors = {
                {"Bug", "#97AD64"},
                {"Electric", "#FCFF5F"},
                {"Fire", "#BA5F75"},
                {"Flying", "#C97AB2"},
                {"Grass", "#59B980"},
                {"Ground", "#C77252"},
                {"Normal", "#727272"},
                {"Poison", "#634BD0"},
                {"Water", "#439F8E"}
        };

        String[] headers = {"#", "NAME", "TYPE 1", "TYPE 2", "JAPANESE", "OFFICIAL ROM."};

        String[][] rows = {
                {"1", "Bulbasaur", "Grass", "Poison", "フシギダネ", "Bulbasaur"},
                {"2", "Ivysaur", "Grass", "Poison", "フシギソウ", "Ivysaur"},
                {"3", "Venusaur", "Grass", "Poison", "フシギバナ", "Venusaur"},
                {"4", "Charmander", "Fire", "", "ヒトカゲ", "Hitokage"},
                {"5", "Charmeleon", "Fire", "", "リザード", "Lizardo"},
                {"6", "Charizard", "Fire", "Flying", "リザードン", "Lizardon"},
                {"7", "Squirtle", "Water", "", "ゼニガメ", "Zenigame"},
                {"8", "Wartortle", "Water", "", "カメール", "Kameil"},
                {"9", "Blastoise", "Water", "", "カメックス", "Kamex"},
                {"10", "Caterpie", "Bug", "", "キャタピー", "Caterpie"},
                {"11", "Metapod", "Bug", "", "トランセル", "Trancell"},
                {"12", "Butterfree", "Bug", "Flying", "バタフリー", "Butterfree"},
                {"13", "Weedle", "Bug", "Poison", "ビードル", "Beedle"},
                {"14", "Kakuna", "Bug", "Poison", "コクーン", "Cocoon"},
                {"15", "Beedrill", "Bug", "Poison", "スピアー", "Spear"},
                {"16", "Pidgey", "Normal", "Flying", "ポッポ", "Poppo"},
                {"17", "Pidgeotto", "Normal", "Flying", "ピジョン", "Pigeon"},
                {"18", "Pidgeot", "Normal", "Flying", "ピジョット", "Pigeot"},
                {"19", "Rattata", "Normal", "", "コラッタ", "Koratta"},
                {"20", "Raticate", "Normal", "", "ラッタ", "Ratta"},
                {"21", "Spearow", "Normal", "Flying", "オニスズメ", "Onisuzume"},
                {"22", "Fearow", "Normal", "Flying", "オニドリル", "Onidrill"},
                {"23", "Ekans", "Poison", "", "アーボ", "Arbo"},
                {"24", "Arbok", "Poison", "", "アーボック", "Arbok"},
                {"25", "Pikachu", "Electric", "", "ピカチュウ", "Pikachu"},
                {"26", "Raichu", "Electric", "", "ライチュウ", "Raichu"},
                {"27", "Sandshrew", "Ground", "", "サンド", "Sand"},
                {"28", "Sandslash", "Ground", "", "サンドパン", "Sandpan"}
        };

        this.table = Table.create()
                .headers(headers)
                .rows(rows)
                .border(StandardBorder.NormalBorder)
                .borderStyle(re.newStyle().foreground(Color.color("238")))
                .styleFunc(new StyleFunc() {
                    @Override
                    public Style apply(int row, int col) {
                        if (row == 0) {
                            return headerStyle;
                        }

                        int rowIndex = row - 1;
                        if (rowIndex < 0 || rowIndex >= rows.length) {
                            return baseStyle;
                        }

                        if ("Pikachu".equals(rows[rowIndex][1])) {
                            return selectedStyle;
                        }

                        boolean even = row % 2 == 0;

                        if (col == 2 || col == 3) {
                            String[][] c = even ? dimTypeColors : typeColors;
                            if (col >= rows[rowIndex].length) {
                                return baseStyle;
                            }

                            String type = rows[rowIndex][col];
                            for (String[] pair : c) {
                                if (pair[0].equals(type)) {
                                    return baseStyle.foreground(Color.color(pair[1]));
                                }
                            }
                            return baseStyle;
                        }

                        if (even) {
                            return baseStyle.foreground(Color.color("245"));
                        }
                        return baseStyle.foreground(Color.color("252"));
                    }
                })
                .border(StandardBorder.ThickBorder);
    }

    @Override
    public Command init() {
        return null;
    }

    @Override
    public UpdateResult<? extends Model> update(com.williamcallahan.tui4j.compat.bubbletea.Message msg) {
        if (msg instanceof WindowSizeMessage windowSizeMsg) {
            table = table.width(windowSizeMsg.width());
            table = table.height(windowSizeMsg.height());
        } else if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            if ("q".equals(key) || "ctrl+c".equals(key)) {
                return UpdateResult.from(this, QuitMessage::new);
            }
        }
        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        return "\n" + table.toString() + "\n";
    }

    public static void main(String[] args) {
        new Program(new TableResizeExample()).run();
    }
}
