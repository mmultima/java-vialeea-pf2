package com.myapp.root.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Generated;

import com.myapp.root.data.Ancestry;
import com.myapp.root.data.Background;
import com.myapp.root.data.CharacterClass;
import com.myapp.root.data.CharacterClassLong;
import com.myapp.root.data.Feat;
import com.myapp.root.data.Heritage;
import com.myapp.root.data.Spell;
import com.myapp.root.data.Trait;
import com.myapp.root.data.equipment.Armor;
import com.myapp.root.data.equipment.Gear;
import com.myapp.root.data.equipment.Weapon;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

@RestController
@RequestMapping("/nethys")
public class NethysController {
    @GetMapping(path="/classes")
    public List<CharacterClass> keycloak() {
/*      
        try {
            //String url = "https://example.com"; // Replace with the desired URL
            String url = "https://2e.aonprd.com/Classes.aspx";
            Document document = Jsoup.connect(url).get();
            //String html = document.html();

            //Jsoup.parse()

            Elements data = document.body().getAllElements();
//            Elements data = document.body().getElementsByClass("rtileTitle");

            System.err.println("############### Starting Elements");
            for (Element el : data) {
                System.err.println("### Element: " + el.text());
            }
            System.err.println("############### Ending Elements");

            //for()
            //class="rtileTitle"

            //System.err.println(html);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
*/

        List<CharacterClass> characterClasses = new ArrayList<>();

        try {
            URL url = new URL("https://2e.aonprd.com/Classes.aspx"); // Replace with the desired URL
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            boolean classStart = false;
            boolean prevWasMatch = false;

            Queue<String> queue = new LinkedList<>();
            int id = 0;
            int feats = 0;
            String name = null;    
            while ((line = br.readLine()) != null) {
                if (line.contains("Click here to view classes in a table")) {
                    classStart = true;
                }

                queue.add(line);
                if (queue.size() > 12) {
                    queue.poll();
                }

                if (line.contains("rtileTitle") && classStart) {

                    
                    for (String q: queue) {
                        if (q.contains("ID=")) {
                            id = Integer.parseInt(extractNumber(q));
                        } else if (q.contains("Traits=")) {
                            feats = Integer.parseInt(extractNumber(q));
                        } 
                        System.out.println("Queue: " + q);
                    }
                    System.out.println(line);
                    prevWasMatch = true;                    
                } else if (prevWasMatch) {
                    System.out.println("Class?: " + line);
                    name = line.trim();
                    CharacterClass characterClass = new CharacterClass(name, id, feats);
                    characterClasses.add(characterClass);
                    prevWasMatch = false;
                } else {
                    prevWasMatch = false;
                }
                /*
                prevPrevPrevPrevLine = prevPrevPrevLine;
                prevPrevPrevLine = prevPrevLine;
                prevPrevLine = prevLine;
                prevLine = line;
                */
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return characterClasses;
    }

    public static String extractNumber(String line) {
        Pattern pattern = Pattern.compile("ID=(\\d+)|Traits=(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            if (matcher.group(1) != null) {
                return matcher.group(1);
            } else if (matcher.group(2) != null) {
                return matcher.group(2);
            }
        }
        return null;
    }

    @GetMapping(path="/featlist/{trait}")
    public List<Feat> featList(@PathVariable String trait) throws IOException {
        return getFeatListInternal(trait);
    }

    @GetMapping(path="/details")
    public List<Feat> details () throws IOException {
        return getFeatListInternal("Sorcerer");
    }
    
    private List<Feat> getFeatListInternal(String trait) throws IOException {
        String value = "";

        List<Feat> feats = new ArrayList<>();

        URL url = new URL("https://example.com"); // Replace with the desired URL

        url = new URL("https://elasticsearch.aonprd.com/aon/_search?track_total_hits=true");

        for (int i = 0; i < 4; i++) {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Set request headers, if needed
            connection.setRequestProperty("Content-Type", "application/json");
            //connection.setRequestProperty("Authorization", "Bearer <your_token>");

            // Set request body, if needed
            String requestBody = "{\"key\": \"value\"}";

            int startLevel = i * 5 + 1;
            int endLevel = i * 5 + 5;

            System.out.println("Start Level: " + startLevel + " End Level: " + endLevel);

            //requestBody = "{'query':{'function_score':{'query':{'bool':{'filter':[{'range':{'level':{'gte':1}}},{'range':{'level':{'lte':5}}},{'query_string':{'query':'category:feat trait:(\'Sorcerer\') NOT trait:kingdom','default_operator':'AND','fields':['name','legacy_name','remaster_name','text^0.1','trait_raw','type'],'minimum_should_match':0}},{'bool':{'must_not':{'exists':{'field':'remaster_id'}}}}],'must_not':[{'term':{'exclude_from_search':true}}]}},'boost_mode':'multiply','functions':[{'filter':{'terms':{'type':['Ancestry','Class','Versatile Heritage']}},'weight':1.2},{'filter':{'terms':{'type':['Trait']}},'weight':1.05}]}},'size':50,'sort':[{'level':{'order':'asc'}},{'name.keyword':{'order':'asc'}},'_doc'],'_source':false,'aggs':{'group1':{'composite':{'sources':[{'field1':{'terms':{'field':'type','missing_bucket':true}}}],'size':10000}}}}";
            // Working query for single // requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"range\":{\"level\":{\"gte\":1}}},{\"range\":{\"level\":{\"lte\":5}}},{\"query_string\":{\"query\":\"category:feat trait:(\\\"Sorcerer\\\") NOT trait:kingdom\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":50,\"sort\":[{\"level\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
            requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"range\":{\"level\":{\"gte\":" + startLevel + "}}},{\"range\":{\"level\":{\"lte\":" + endLevel + "}}},{\"query_string\":{\"query\":\"category:feat trait:(\\\"" + trait + "\\\") NOT trait:kingdom\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":50,\"sort\":[{\"level\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
    //{"query":{"function_score":{"query":{"bool":{"filter":[{"range":{"level":{"gte":1}}},{"range":{"level":{"lte":5}}},{"query_string":{"query":"category:feat trait:(\"Sorcerer\") NOT trait:kingdom","default_operator":"AND","fields":["name","legacy_name","remaster_name","text^0.1","trait_raw","type"],"minimum_should_match":0}},{"bool":{"must_not":{"exists":{"field":"remaster_id"}}}}],"must_not":[{"term":{"exclude_from_search":true}}]}},"boost_mode":"multiply","functions":[{"filter":{"terms":{"type":["Ancestry","Class","Versatile Heritage"]}},"weight":1.2},{"filter":{"terms":{"type":["Trait"]}},"weight":1.05}]}},"size":50,"sort":[{"level":{"order":"asc"}},{"name.keyword":{"order":"asc"}},"_doc"],"_source":false,"aggs":{"group1":{"composite":{"sources":[{"field1":{"terms":{"field":"type","missing_bucket":true}}}],"size":10000}}}}

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            // Get response code
            int responseCode = connection.getResponseCode();

            // Read response body
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseBody = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseBody.append(line);
            }
            br.close();

            // Handle response
            System.out.println("Response Code: " + responseCode);
            //System.out.println("Response Body: " + responseBody.toString());
            for (Feat s: getFeats(responseBody.toString())) {
                System.out.println("Feat: " + s.getName() + " ID: " + s.getId());
                feats.add(s);
            }
            // Close connection
            connection.disconnect();
            
        


            value = value + responseBody.toString();

        }


        return feats;
        //return value;
        //'/Classes.aspx?ID=1
        //='/Feats.aspx?Traits=7
    }

    @GetMapping(path="/weaponList/{trait}")
    public List<Weapon> weaponList(@PathVariable String trait) throws IOException {
        return getWeaponListInternal(trait);
    }

    private List<Weapon> getWeaponListInternal(String trait) throws IOException {
        String value = "";

        List<Weapon> weapons = new ArrayList<>();

        URL url = new URL("https://example.com"); // Replace with the desired URL

        url = new URL("https://elasticsearch.aonprd.com/aon/_search?track_total_hits=true");

        String category = trait;

        for (int i = 0; i < 1; i++) {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Set request headers, if needed
            connection.setRequestProperty("Content-Type", "application/json");
            //connection.setRequestProperty("Authorization", "Bearer <your_token>");

            // Set request body, if needed
            String requestBody = "{\"key\": \"value\"}";

            //int startLevel = i * 5 + 1;
            //int endLevel = i * 5 + 5;

            //System.out.println("Start Level: " + startLevel + " End Level: " + endLevel);

            //requestBody = "{'query':{'function_score':{'query':{'bool':{'filter':[{'range':{'level':{'gte':1}}},{'range':{'level':{'lte':5}}},{'query_string':{'query':'category:feat trait:(\'Sorcerer\') NOT trait:kingdom','default_operator':'AND','fields':['name','legacy_name','remaster_name','text^0.1','trait_raw','type'],'minimum_should_match':0}},{'bool':{'must_not':{'exists':{'field':'remaster_id'}}}}],'must_not':[{'term':{'exclude_from_search':true}}]}},'boost_mode':'multiply','functions':[{'filter':{'terms':{'type':['Ancestry','Class','Versatile
            requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"bool\":{\"should\":[{\"terms\":{\"weapon_category\":[\"" + category + "\"]}}]}},{\"query_string\":{\"query\":\"category:weapon\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":250,\"sort\":[{\"weapon_type\":{\"order\":\"asc\"}},{\"weapon_category\":{\"order\":\"desc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
 
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            // Get response code
            int responseCode = connection.getResponseCode();

            // Read response body
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseBody = new StringBuilder();
            while ((line = br.readLine()) != null) {
                //responseBody.append(line);
                System.out.println("Line: " + line);
                //"sort":["ranged","simple","sling",29033]
                Pattern pattern = Pattern.compile("_id\":\"weapon\\-(\\d+)\".+?sort\":\\[\"(\\w+)\",\"(\\w+)\",\"(.+?)\",(\\d+)\\]");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    //description = matcher.group(1);
                    //System.out.println("Match: " + matcher.group(1));
                    Weapon weapon = new Weapon();
                    weapon.setId(Integer.parseInt(matcher.group(1)));
                    weapon.setName(matcher.group(4));
                    //Integer.parseInt(matcher.group(4)), matcher.group(1), matcher.group(2), matcher.group(3));
                    weapons.add(weapon);
                }

            }
            br.close();

            connection.disconnect();
        }

        return weapons;
    }

    @GetMapping(path="/armorList/{category}")
    public List<Armor> armorList(@PathVariable String category) throws IOException {
        return getArmorListInternal(category);
    }

    private List<Armor> getArmorListInternal(String category) throws IOException {
        String value = "";

        List<Armor> armors = new ArrayList<>();

        URL url = new URL("https://example.com"); // Replace with the desired URL

        url = new URL("https://elasticsearch.aonprd.com/aon/_search?track_total_hits=true");

        for (int i = 0; i < 1; i++) {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Set request headers, if needed
            connection.setRequestProperty("Content-Type", "application/json");
            //connection.setRequestProperty("Authorization", "Bearer <your_token>");

            // Set request body, if needed
            String requestBody = "{\"key\": \"value\"}";
           
            requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"bool\":{\"should\":[{\"terms\":{\"armor_category\":[\"" + category + "\"]}}]}},{\"query_string\":{\"query\":\"category:armor\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":50,\"sort\":[{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
            //                    "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"bool\":{\"should\":[{\"terms\":{\"armor_category\":[\"" + category+ "\"]}}]}},{\"query_string\":{\"query\":\"category:armor\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":50,\"sort\":[{\"ac\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
            //requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"query_string\":{\"query\":\"category:armor\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":50,\"sort\":[{\"ac\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
            //requestBody = "{'query':{'function_score':{'query':{'bool':{'filter':[{'range':{'level':{'gte':1}}},{'range':{'level':{'lte':5}}},{'query_string':{'query':'category:feat trait:(\'Sorcerer\') NOT trait:kingdom','default_operator':'AND','fields':['name','legacy_name','remaster_name','text^0.1','trait_raw','type'],'minimum_should_match':0}},{'bool':{'must_not':{'exists':{'field':'remaster_id'}}}}],'must_not':[{'term':{'exclude_from_search':true}}]}},'boost_mode':'multiply','functions':[{'filter':{'terms':{'type':['Ancestry','Class','Versatile
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            // Get response code
            int responseCode = connection.getResponseCode();
            
            // Read response body
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseBody = new StringBuilder();
            while((line = br.readLine()) != null) {
                //responseBody.append(line);
                System.out.println("Line: " + line);
                Pattern pattern = Pattern.compile("_id\":\"armor\\-(\\d+)\".+?sort\":\\[\"(.+?)\",(\\d+)\\]");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    //description = matcher.group(1);
                    //System.out.println("Match: " + matcher.group(1));
                    Armor armor = new Armor();
                    armor.setId(Integer.parseInt(matcher.group(1)));
                    armor.setName(matcher.group(2));
                    //Integer.parseInt(matcher.group(4)), matcher.group(1), matcher.group(2), matcher.group(3));
                    armors.add(armor);
                }
            }



        }
        return armors;

    }

    @GetMapping(path="/gearList/{category}")
    public List<Gear> gearList(String category) throws IOException {
        return getGearListInternal(category);
    }

    private List<Gear> getGearListInternal(String category) throws IOException {
        String value = "";

        List<Gear> gears = new ArrayList<>();

        URL url = new URL("https://example.com"); // Replace with the desired URL

        url = new URL("https://elasticsearch.aonprd.com/aon/_search?track_total_hits=true");

        for (int i = 0; i < 1; i++) {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Set request headers, if needed
            connection.setRequestProperty("Content-Type", "application/json");
            //connection.setRequestProperty("Authorization", "Bearer <your_token>");

            // Set request body, if needed
            String requestBody = "{\"key\": \"value\"}";

            //int startLevel = i * 5 + 1;
            //int endLevel = i * 5 + 5;

            //System.out.println("Start Level: " + startLevel + " End Level: " + endLevel);
            requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"query_string\":{\"query\":\"category:(armor OR equipment OR shield OR siege-weapon OR vehicle OR weapon) item_category:\\\"Adventuring Gear\\\"\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":300,\"sort\":[{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
            //requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"query_string\":{\"query\":\"category:(armor OR equipment OR shield OR siege-weapon OR vehicle OR weapon) item_category:\\\"Adventuring Gear\\\"\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":10000,\"sort\":[{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"search_after\":[\"clothing (desert)\",13874]}";
            //requestBody = "{'query':{'function_score':{'query':{'bool':{'filter':[{'range':{'level':{'gte':1}}},{'range':{'level':{'lte':5}}},{'query_string':{'query':'category:feat trait:(\'Sorcerer\') NOT trait:kingdom','default_operator':'AND','fields':['name','legacy_name','remaster_name','text^0.1','trait_raw','type'],'minimum_should_match':0}},{'bool':{'must_not':{'exists':{'field':'remaster_id'}}}}],'must_not':[{'term':{'exclude_from_search':true}}]}},'boost_mode':'multiply','functions':[{'filter':{'terms':{'type':['Ancestry','Class','Versatile
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            // Get response code
            int responseCode = connection.getResponseCode();
            
            // Read response body
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseBody = new StringBuilder();
            while((line = br.readLine()) != null) {
                //responseBody.append(line);
                System.out.println("Line: " + line);
                //Pattern pattern = Pattern.compile("_id\":\"equipment\\-(\\d+)(\"|\\-).+?sort\":\\[\"(.+?)\",(\\d+)\\]");
                Pattern pattern = Pattern.compile("_id\":\"equipment\\-(\\d+)(\"|\\-(\\d+)).+?sort\":\\[\"(.+?)\",(\\d+)\\]");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    //description = matcher.group(1);
                    //System.out.println("Match: " + matcher.group(1));
                    Gear gear = new Gear();

                    //String idString = matcher.group(3) != null ? matcher.group(3) : matcher.group(1);

                    String idString = matcher.group(1);

                    gear.setId(Integer.parseInt(idString));
                    gear.setName(matcher.group(4));

                    if (matcher.group(3) != null) {
                        gear.setSubId(Integer.parseInt(matcher.group(3)));
                    } else {
                        gear.setSubId(0);
                    }

                    System.out.print("Match: " + gear.getId() + " groupcount " + matcher.groupCount());
                    for (int j = 0; j< matcher.groupCount(); j++) {
                        System.out.print(" Group: " + j + " " + matcher.group(j));
                    }
                    System.out.println();


                    //Integer.parseInt(matcher.group(4)), matcher.group(1), matcher.group(2), matcher.group(3));
                    gears.add(gear);
                }
            }


        }

        return gears;
    }

    public void feats (int trait) {
        //https://2e.aonprd.com/Feats.aspx?Traits=148&values-from=level%3A20&values-to=level%3A20&sort=level-asc+name-asc&display=grouped&group-fields=level&link-layout=vertical-with-summary
        try {
            URL url = new URL("https://2e.aonprd.com/Feats.aspx?Traits=" + trait + "&values-from=level%3A20&values-to=level%3A20&sort=level-asc+name-asc&display=grouped&group-fields=level&link-layout=vertical-with-summary"); // Replace with the desired URL

            //url.

            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Feat> getFeats(String line) {


        //System.out.println("Line: " + line.substring(0, 10));

        //String myval ="{\"took\":2,\"timed_out\":false,\"_shards\":{\"total\":1,\"successful\":1,\"skipped\":0,\"failed\":0},\"hits\":{\"total\":{\"value\":19,\"relation\":\"eq\"},\"max_score\":null,\"hits\":[{\"_index\":\"aon36\",\"_id\":\"feat-1810\",\"_score\":null,\"sort\":[1,\"ancestral blood magic\",16735]},\"";

        Pattern pattern = Pattern.compile("(\\d+)\",\"_score\":null,\"sort\":\\[\\d+,\"(.*?)\"");
//        List<String> sortList = new ArrayList<>();
        List<Feat> sortList = new ArrayList<>();
        //Pattern pattern = Pattern.compile("\"sort\":\\[\\d+,\"(.*)\"");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
//            sortList.add(matcher.group(2));
            Feat feat = new Feat(Integer.parseInt(matcher.group(1)), matcher.group(2));
            sortList.add(feat);

        }

        return sortList;
    }

    //This should return all feats, but that's a bit too heavy a call.
    @GetMapping(path="/feats")
    public List<Feat> feats() {
        List<Feat> feats = new ArrayList<>();
        //feats.add(new Feat(1, "Feat 1"));
        //feats.add(new Feat(2, "Feat 2"));
        //feats.add(new Feat(3, "Feat 3"));
        //feats.add(new Feat(4, "Feat 4"));
        //feats.add(new Feat(5, "Feat 5"));
        return feats;
    }

    @GetMapping(path="/feats/{id}")
    public Feat feat(@PathVariable int id) throws IOException {

        Feat feat = null;
        String value = "";

        URL url = new URL("https://2e.aonprd.com/Feats.aspx?ID=" + id); // Replace with the desired URL


        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;

        while ((line = br.readLine()) != null) {
            i++;

            //System.out.println(line);
            if (line.contains("Sources.aspx")) {
                if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                    System.out.println("Line: " + i);
                    System.out.println(line);

                    String description = null;
                    String name = null;
                    int id2 = 0;                    

                    //Pattern pattern = Pattern.compile("(\\d+)\",\"_score\":null,\"sort\":\\[\\d+,\"(.*?)\"");
                    Pattern pattern = Pattern.compile("\\<hr \\/\\>(.*?)\\<\\/span\\>");
                    //        List<String> sortList = new ArrayList<>();
                    //List<Feat> sortList = new ArrayList<>();
                            //Pattern pattern = Pattern.compile("\"sort\":\\[\\d+,\"(.*)\"");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        description = matcher.group(1);
                        //System.out.println("Match: " + matcher.group(1));
                        //Feat feat = new Feat(Integer.parseInt(matcher.group(1)), matcher.group(2));
                        
                    }

                    Pattern pattern2 = Pattern.compile("<a href=\"Feats.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                    Matcher matcher2 = pattern2.matcher(line);
                    if (matcher2.find()) {
                        id2 = Integer.parseInt(matcher2.group(1));
                        name = matcher2.group(2);

                        //System.out.println(matcher2.group(1));
                        //System.out.println(matcher2.group(2));
                    }


                    Pattern pattern3 = Pattern.compile("<a href=\"\\/Traits.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                    Matcher matcher3 = pattern3.matcher(line);
                    List<Trait> traits = new ArrayList<>();
                    while (matcher3.find()) {
                        Trait trait = new Trait(Integer.parseInt(matcher3.group(1)), matcher3.group(2));

                        traits.add(trait);

                        System.out.println("Trait");
                        System.out.println(matcher3.group(1));
                        System.out.println(matcher3.group(2));
                    }
                    //Spellshape Mastery

                    //Feat feat = new Feat(Integer.parseInt(matcher.group(1)), matcher.group(2));
                    feat = new Feat(id2, name, description, traits);

                }
                //System.out.println("Line: " + i);
                //System.out.println(line);
            }

            value = value + line;
        }

        //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connection.setRequestMethod("GET");
        
        br.close();

        return  feat;
    }


    @GetMapping(path="/equipment/weapons/{id}")
    public Weapon weapon(@PathVariable int id) throws IOException {
        Weapon weapon = null;

        URL url = new URL("https://2e.aonprd.com/Weapons.aspx?ID=" + id); // Replace with the desired URL

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;

        while ((line = br.readLine()) != null) {        
            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                System.out.println("Line: " + i);
                System.out.println(line);

                int id2 = 0;
                String name = null;
                String description = null;

                Pattern pattern = Pattern.compile("\\<hr \\/\\>(.*?)\\<h2");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    description = matcher.group(1);
                }

                Pattern pattern2 = Pattern.compile("<a href=\"Weapons.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()) {
                    id2 = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(2);
                }

                weapon = new Weapon(id2, name, description);

                //<b>Price</b> 4 gp; <b>Damage</b> 1d8 S; <b>Bulk</b> 1<br /><b>Hands</b> 1<br /><b>Type</b> Melee; <b>Category</b> Martial; <b>Group</b> <u><a href="WeaponGroups.aspx?ID=15">Sword</a>
/*
                Pattern detailsPattern = Pattern.compile(
                    "<b>Price</b> (\\d+) gp; " +
                    "<b>Damage</b> ([^ ]+) [^;]+; " +
                    "<b>Bulk</b> (\\d+)<br />" +
                    "<b>Hands</b> (\\d+)<br />" +
                    "<b>Type</b> ([^;]+); " +
                    "<b>Category</b> ([^;]+); " +
                    "<b>Group</b> <u><a href=\"WeaponGroups.aspx\\?ID=\\d+\">([^<]+)</a>"
                );
                
                Matcher detailsMatcher = detailsPattern.matcher(line);
                if (detailsMatcher.find()) {
                    String price = detailsMatcher.group(1);
                    String damage = detailsMatcher.group(2);
                    String bulk = detailsMatcher.group(3);
                    String hands = detailsMatcher.group(4);
                    String type = detailsMatcher.group(5);
                    String category = detailsMatcher.group(6);
                    String group = detailsMatcher.group(7);
                
                    System.out.println("Price: " + price);
                    System.out.println("Damage: " + damage);
                    System.out.println("Bulk: " + bulk);
                    System.out.println("Hands: " + hands);
                    System.out.println("Type: " + type);
                    System.out.println("Category: " + category);
                    System.out.println("Group: " + group);
                }     
                    */           
                Pattern detailsPattern = Pattern.compile(
                    "<b>Price</b> (\\d+) (\\w)p; " 
                    + "<b>Damage</b> ([^ ]+) [^;]+; " 
                    + "<b>Bulk</b> ([^<]+)<br />" 
                    + "<b>Hands</b> ([^<;]+);? ?(<br />)?" 
                    + "(?:<b>Range</b> (\\d+) ft.; )?" 
                    + "(?:<b>Reload</b> (\\d+)<br />)?" 
                    + "<b>Type</b> ([^;]+); " 
                    + "<b>Category</b> ([^;]+); " 
                    + "<b>Group</b> <u><a href=\"WeaponGroups.aspx\\?ID=\\d+\">([^<]+)</a>"
                );
                
                Matcher detailsMatcher = detailsPattern.matcher(line);
                if (detailsMatcher.find()) {
                    String price = detailsMatcher.group(1);
                    String damage = detailsMatcher.group(3);
                    String bulk = detailsMatcher.group(4);
                    String hands = detailsMatcher.group(5);
                    String range = detailsMatcher.group(7);
                    String reload = detailsMatcher.group(8);
                    String type = detailsMatcher.group(9);
                    String category = detailsMatcher.group(10);
                    String group = detailsMatcher.group(11);
                
                    weapon.setPriceInCopper(Integer.parseInt(price) * (detailsMatcher.group(2).equals("g") ? 100 : detailsMatcher.group(2).equals("s") ? 10 : 1));
                    weapon.setDamage(damage);
                    weapon.setBulk(bulk);
                    weapon.setHands(hands);
                    weapon.setRange(range);
                    //weapon.setRange(range != null ? Integer.parseInt(range) : 0);
                    weapon.setReload(reload);
                    //weapon.setReload(reload != null ? Integer.parseInt(reload) : 0);
                    weapon.setType(type);
                    weapon.setCategory(category);
                    weapon.setGroup(group);

                    //(detailsMatcher.group(2).equals("g") ? 100 : detailsMatcher.group(2).equals("s") ? 10 : 1)
                    /*
                    System.out.println("Price: " + price);
                    System.out.println("Damage: " + damage);
                    System.out.println("Bulk: " + bulk);
                    System.out.println("Hands: " + hands);
                    System.out.println("Range: " + (range != null ? range : "N/A"));
                    System.out.println("Reload: " + (reload != null ? reload : "N/A"));
                    System.out.println("Type: " + type);
                    System.out.println("Category: " + category);
                    System.out.println("Group: " + group);
                    */
                } else {
                    //Maybe it's ammunition
                    Pattern ammunitionPattern = Pattern.compile("<b>Category<\\/b> Ammunition; ");
                    Matcher ammunitionMatcher = ammunitionPattern.matcher(line);
                    if (ammunitionMatcher.find()) {
                        weapon.setCategory("Ammunition");

                        Pattern bulkPattern = Pattern.compile(">Bulk<\\/b> ([^<]+)<");
                        Matcher bulkMatcher = bulkPattern.matcher(line);
                        if (bulkMatcher.find()) {
                            weapon.setBulk(bulkMatcher.group(1));
                        }

                        Pattern pricePattern = Pattern.compile(">Price<\\/b> (\\d+) (\\w)p (\\(price for (\\d+))");
                        Matcher priceMatcher = pricePattern.matcher(line);
                        if (priceMatcher.find()) {
                            weapon.setPriceInCopper(Integer.parseInt(priceMatcher.group(1)) * (priceMatcher.group(2).equals("g") ? 100 : priceMatcher.group(2).equals("s") ? 10 : 1));
                            weapon.setPurchaseAmount(Integer.parseInt(priceMatcher.group(4)));
                        }
                    }
                }

                Pattern twoHandedPattern = Pattern.compile(">Two-Hand (\\dd\\d+)<");
                Matcher twoHandedMatcher = twoHandedPattern.matcher(line);
                if (twoHandedMatcher.find()) {
                    //System.out.println("Two-Handed Damage: " + twoHandedMatcher.group(1));
                    weapon.setTwoHandedDamage(twoHandedMatcher.group(1));
                }

                Pattern deadlyPattern = Pattern.compile(">Deadly d(\\d+):?<");
                Matcher deadlyMatcher = deadlyPattern.matcher(line);
                if (deadlyMatcher.find()) {
                    //System.out.println("Deadly: " + deadlyMatcher.group(1));
                    weapon.setDeadlyDice(Integer.parseInt(deadlyMatcher.group(1)));
                }
            }        
        }
        return weapon;
    }

    @GetMapping(path="/equipment/armor/{id}")
    public Armor armor(@PathVariable int id) throws IOException {
        Armor armor = new Armor();
        URL url = new URL("https://2e.aonprd.com/Armor.aspx?ID=" + id); // Replace with the desired URL
        
        InputStream is = url.openStream(); 
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;

        while ((line = br.readLine()) != null) {
            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
            //if (line.contains("Buckle")) {
//                System.out.println("Line: " + i);
//                System.out.println(line);

                int id2 = 0;
                String name = null;
                String description = null;

                Pattern pattern = Pattern.compile("\\<hr \\/\\>(.*?)\\<h2");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    description = matcher.group(1);
                }

                Pattern pattern2 = Pattern.compile("<a href=\"Armor.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()) {
                    id2 = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(2);
                }

                armor = new Armor(id2, name, description);

                //<b>Price</b> 3 gp; <b>AC Bonus</b> +2; <b>Dex Cap</b> +3; <b>Check Penalty</b> -1; <b>Speed Penalty</b> &mdash;<br /><b>Strength</b> +1; <b>Bulk</b> 1; <b>Category</b> Light; <b>Group</b> <u><a href="ArmorGroups.aspx?ID=3">Leather</a></u>

                Pattern statPattern = Pattern.compile("Price<\\/b> (\\d+) (\\w)p; <b>AC Bonus<\\/b> \\+(\\d+); <b>Dex Cap<\\/b> \\+(\\d+); <b>Check Penalty<\\/b> (&mdash;|\\-(\\d+)); <b>Speed Penalty</b> (&mdash;|\\-(\\d+) ft.)<br \\/><b>Strength<\\/b> (&mdash;|\\+(\\d+)); <b>Bulk<\\/b> (.*?); <b>Category<\\/b> (\\w+); <b>Group<\\/b> <u><a href=\"ArmorGroups.aspx\\?ID=\\d+\">(\\w+)<\\/a>");
                Matcher statMatcher = statPattern.matcher(line);
                if (statMatcher.find()) {
                    armor.setPriceInCopper((statMatcher.group(2).equals("g") ? 100 : statMatcher.group(2).equals("s") ? 10 : 1) * Integer.parseInt(statMatcher.group(1)));
                    
                    armor.setAcBonus(Integer.parseInt(statMatcher.group(3)));
                    armor.setDexCap(Integer.parseInt(statMatcher.group(4)));
                    armor.setCheckPenalty(statMatcher.group(5).equals("&mdash;") ? 0 : Integer.parseInt(statMatcher.group(6)));
                    armor.setSpeedPenalty( statMatcher.group(7).equals("&mdash;") ? 0 : Integer.parseInt(statMatcher.group(8)));
                    armor.setStrength(statMatcher.group(9).equals("&mdash;") ? 0 : Integer.parseInt(statMatcher.group(10)));
                    armor.setBulk(statMatcher.group(11));
                    armor.setCategory(statMatcher.group(12)); 
                    armor.setGroup(statMatcher.group(13)); //TODO: Maybe store the armor group id instead of the name
                    
                }
            }
            //System.out.println(line);
        }

        return armor;
    }

    @GetMapping(path="/equipment/gear/{id}")
    public Gear gear(@PathVariable int id) throws IOException {
        Gear gear = new Gear();

        URL url = new URL("https://2e.aonprd.com/Equipment.aspx?ID=" + id); // Replace with the desired URL

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;

        while ((line = br.readLine()) != null) {
            i++;
            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                System.out.println("Line: " + i);
                System.out.println(line);

                int id2 = id;
                String name = null;
                String description = null;

                Pattern pattern = Pattern.compile("\\<hr \\/\\>(.*?)(?:\\<\\/span\\>|<br \\/><h2)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    description = matcher.group(1);
                }

                System.out.println("Description: " + description);

                Pattern pattern2 = Pattern.compile("png\"><\\/a><\\/span>(.*?)<span style=\"margin\\-left\\:auto");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()) {
                    //id2 = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(1);
                }

                Pattern pattern3 = Pattern.compile("\\/span>([^<]*?)<span style=\"margin\\-left\\:auto");
                Matcher matcher3 = pattern3.matcher(line);

                List<String> subItemNames = new ArrayList<>();
                while (matcher3.find()) {
                    subItemNames.add(matcher3.group(1));
                }

                gear = new Gear(id2, name, description);

                gear.setSubItemNames(subItemNames);
            }
        }

        return gear;
    }

    @GetMapping(path="/spells/{id}")
    public Spell spells(@PathVariable int id) throws IOException {
        Spell spell = new Spell();

        URL url = new URL("https://2e.aonprd.com/Spells.aspx?ID=" + id); // Replace with the desired URL

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;


        //<span class="k-icon likeButton" data-id="spell-1768" onClick="toggleLike()"></span>Hymn of Healing<span

        while ((line = br.readLine()) != null) {
            i++;
            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                System.out.println("Line: " + i);
                System.out.println(line);

                int id2 = 0;
                String name = null;
                String description = null;

                Pattern pattern = Pattern.compile("\\<hr \\/\\>(.*?)\\<\\/span\\>");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    description = matcher.group(1);
                }

                Pattern pattern2 = Pattern.compile("(\\d+)\" onClick=\"toggleLike\\(\\)\"><\\/span>(.*?)<span");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()) {
                    id2 = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(2);
                }

                spell = new Spell(id2, name, description);

                Pattern levelPattern = Pattern.compile("<span style=\"margin-left:auto; margin-right:0\">(Spell|Focus|Cantrip) (\\d+)</span>");
                Matcher levelMatcher = levelPattern.matcher(line);
                if (levelMatcher.find()) {
                    spell.setLevel(Integer.parseInt(levelMatcher.group(2)));
                }

                Pattern traitPattern = Pattern.compile("<a href=\"\\/Traits.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                Matcher traitMatcher = traitPattern.matcher(line);
                List<Trait> traits = new ArrayList<>();
                while (traitMatcher.find()) {
                    Trait trait = new Trait(Integer.parseInt(traitMatcher.group(1)), traitMatcher.group(2));
                    traits.add(trait);
                    //System.out.println("Trait " + traitMatcher.group(1) + " " + traitMatcher.group(2));
                }
                spell.setTraits(traits);
            }
        }

        return spell;
    }

    //@GetMapping(path="/skills")
    //public String skills() throws IOException {

        /*
        String value = "";

        URL url = new URL("https://2e.aonprd.com/Skills.aspx"); // Replace with the desired URL

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;

        while ((line = br.readLine()) != null) {
            i++;
            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                System.out.println("Line: " + i);
                System.out.println(line);
            }
            value = value + line;
        }

        return value;
        */
    //}

    @GetMapping(path="/spellLists/{list}/{level}")
    public List<Spell> spellList(@PathVariable String list, @PathVariable int level) throws IOException {
        return getSpellListInternal(list, level);
    }

    private List<Spell> getSpellListInternal(String list, int level) {
        return null;
    }


    @GetMapping(path="/ancestries")
    public List<Ancestry> ancestryList() throws IOException {
        List<Ancestry> ancestries = new ArrayList<>();
        
        URL url = new URL("https://2e.aonprd.com/Ancestries.aspx"); // Replace with the desired URL 

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        
        int i = 0;

        while ((line = br.readLine()) != null) {
            i++;

            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                System.out.println("Line: " + i);
                System.out.println(line);

                String[] raritySplits = line.split("(Rare|Uncommon) Ancestries");
                String[] rarities = {"Common", "Uncommon", "Rare"};

                for (int rarityIndex = 0; rarityIndex < rarities.length; rarityIndex++) {
                    String rarityLine = raritySplits[rarityIndex];
                    
                    int id = 0;
                    String name = null;

                    Pattern pattern2 = Pattern.compile("<a href=\"Ancestries.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                    Matcher matcher2 = pattern2.matcher(rarityLine);
                    while (matcher2.find()) {
                        
                        id = Integer.parseInt(matcher2.group(1));
                        name = matcher2.group(2);
                        if (!name.contains("Click")) {
                            Ancestry ancestry = new Ancestry(name, id);
                            ancestry.setRarity(rarities[rarityIndex]); //TODO: Find the rarity
                            ancestries.add(ancestry);
                        }
                    }
                }
            }
        }
        
        return ancestries;
    }

    @GetMapping(path="/ancestries/{id}")
    public Ancestry getAncestry(@PathVariable String id) throws IOException {
        Ancestry ancestry = null;

        URL url = new URL("https://2e.aonprd.com/Ancestries.aspx?ID=" + id); // Replace with the desired URL

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;


        while ((line = br.readLine()) != null) {
            i++;
            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                //System.out.println("Line: " + i);
                //System.out.println(line);

                int id2 = 0;
                String name = null;
                String description = null;

                Pattern pattern = Pattern.compile("\\<hr \\/\\>(.*?)\\<\\/span\\>");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    description = matcher.group(1);
                }

                Pattern pattern2 = Pattern.compile("<a href=\"Ancestries.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()) {
                    id2 = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(2);
                }

                Pattern traitPattern = Pattern.compile("<a href=\"\\/Traits.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                //<a href="/Traits.aspx?ID=627">Human</a>
                ///><a href="/Traits.aspx?ID=627">Human</a>
                Matcher traitMatcher = traitPattern.matcher(line);
                List<Trait> traits = new ArrayList<>();
                while (traitMatcher.find()) {
                    System.out.println("Trait");
                    Trait trait = new Trait(Integer.parseInt(traitMatcher.group(1)), traitMatcher.group(2));
                    traits.add(trait);
                }
                ancestry = new Ancestry(name, id2);
                //ancestry.setDescription(description);
                ancestry.setTraits(traits);
            }
        }


        return ancestry;
    }

    @GetMapping(path="/heritages/{trait}")
    public List<Heritage> heritageList(@PathVariable String trait) throws IOException {
        return getHeritageListInternal(trait);
    }

    private List<Heritage> getHeritageListInternal(String trait) throws IOException {
        List<Heritage> heritages = new ArrayList<>();

        URL url = new URL("https://2e.aonprd.com/Heritages.aspx?Ancestry=" + trait); // Replace with the desired URL

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;

        while ((line = br.readLine()) != null) {
            i++;

            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                System.out.println("Line: " + i);
                System.out.println(line);

                int id = 0;
                String name = null;

                Pattern pattern2 = Pattern.compile("<a href=\"Heritages.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                Matcher matcher2 = pattern2.matcher(line);
                while (matcher2.find()) {
                    id = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(2);
                    Heritage heritage = new Heritage(id, name);
                    heritages.add(heritage);
                }
            }
        }

        return heritages;
    }

    @GetMapping(path="/backgrounds")
    public List<Background> backgroundList() throws IOException {
        List<Background> backgrounds = new ArrayList<>();

        URL url = new URL("https://2e.aonprd.com/Backgrounds.aspx"); // Replace with the desired URL
        url = new URL("https://elasticsearch.aonprd.com/aon/_search?track_total_hits=true");

        /*
        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
*/
        int i = 0;


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        // Set request headers, if needed
        connection.setRequestProperty("Content-Type", "application/json");
        //connection.setRequestProperty("Authorization", "Bearer <your_token>");

        // Set request body, if needed
        String requestBody = "{\"key\": \"value\"}";

        //int startLevel = i * 5 + 1;
        //int endLevel = i * 5 + 5;

        //System.out.println("Start Level: " + startLevel + " End Level: " + endLevel);

        //requestBody = "{'query':{'function_score':{'query':{'bool':{'filter':[{'range':{'level':{'gte':1}}},{'range':{'level':{'lte':5}}},{'query_string':{'query':'category:feat trait:(\'Sorcerer\') NOT trait:kingdom','default_operator':'AND','fields':['name','legacy_name','remaster_name','text^0.1','trait_raw','type'],'minimum_should_match':0}},{'bool':{'must_not':{'exists':{'field':'remaster_id'}}}}],'must_not':[{'term':{'exclude_from_search':true}}]}},'boost_mode':'multiply','functions':[{'filter':{'terms':{'type':['Ancestry','Class','Versatile
        requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"query_string\":{\"query\":\"category:background is_general_background:true NOT region:* NOT trait:legacy\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":200,\"sort\":[{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";

        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody.getBytes());
        outputStream.flush();
        outputStream.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;

        while ((line = br.readLine()) != null) {
            i++;
            System.out.println(line);

            //if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                //System.out.println("Line: " + i);
                //System.out.println(line);

                int id = 0;
                String name = null;
                String description = null;

                Pattern pattern2 = Pattern.compile("background-(\\d+)\",\"_score\":null,\"sort\":\\[\"([^\"]+)\"");
                Matcher matcher2 = pattern2.matcher(line);
                while (matcher2.find()) {
                    id = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(2);
                    description = "";
                    Background background = new Background(id, name, description);
                    backgrounds.add(background);
                }
            //}
        }

        return backgrounds;
    }

    @GetMapping(path="/backgrounds/{id}")
    public Background background(@PathVariable int id) throws IOException {
        Background background = new Background();

        URL url = new URL("https://2e.aonprd.com/Backgrounds.aspx?ID=" + id); // Replace with the desired URL

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;

        while ((line = br.readLine()) != null) {
            i++;
            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                System.out.println("Line: " + i);
                System.out.println(line);

                int id2 = 0;
                String name = null;
                String description = null;

                Pattern pattern2 = Pattern.compile("\\<hr \\/\\>(.*?)\\<h2");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()) {
                    description = matcher2.group(1);
                }

                Pattern pattern3 = Pattern.compile("<a href=\"Backgrounds.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                Matcher matcher3 = pattern3.matcher(line);
                if (matcher3.find()) {
                    id2 = Integer.parseInt(matcher3.group(1));
                    name = matcher3.group(2);
                }

                background = new Background(id2, name, description);
            }
        }

        return background;
    }

    @GetMapping(path="/classes/{id}")
    public CharacterClassLong classInfo(@PathVariable int id) throws IOException {
        CharacterClassLong classInfo = null;

        //https://2e.aonprd.com/Classes.aspx?ID=32

        URL url = new URL("https://2e.aonprd.com/Classes.aspx?ID=" + id); // Replace with the desired URL

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        int i = 0;


        while ((line = br.readLine()) != null) {
            i++;
            if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                System.out.println("Line: " + i);
                System.out.println(line);

                int id2 = 0;
                String name = null;
                String description = null;

                Pattern pattern = Pattern.compile("\\<hr \\/\\>(.*?)\\<\\/span\\>");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    description = matcher.group(1);
                }

                Pattern pattern2 = Pattern.compile("<a href=\"Classes.aspx\\?ID=(\\d+)\">(.*?)<\\/a>");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()) {
                    id2 = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(2);
                }

                Pattern hpPattern = Pattern.compile("<b>Hit Points: (\\d+)");
                Matcher hpMatcher = hpPattern.matcher(line);
                int hpPerLevel = 0;
                if (hpMatcher.find()) {
                    hpPerLevel = (Integer.parseInt(hpMatcher.group(1)));
                }

                classInfo = new CharacterClassLong(id2, name, description, hpPerLevel);
            }
        }

        classInfo.setId(id);

        return classInfo;
    }

    @GetMapping(path="/spells")
    public List<Spell> spellList(@RequestParam(required = false) String tradition, @RequestParam(required = false) List<String> Trait, @RequestParam(required = false) Integer level) throws IOException {
        if (Trait != null) {
            for (String trait : Trait) {
                System.out.println("Trait: " + trait);
            }
        }
        if (level != null) {
            System.out.println("Level: " + level);
        }

        List<Spell> spells = new ArrayList<>();
        if (tradition != null) {
            System.out.println("Tradition: " + tradition);
        }

        //requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"term\":{\"trait\":{\"value\":\"cantrip\"}}},{\"query_string\":{\"query\":\"category:spell tradition: " + tradition + "\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":100,\"sort\":[{\"spell_type\":{\"order\":\"asc\"}},{\"rank\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}}],\"size\":10000}},\"group2\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}},{\"field2\":{\"terms\":{\"field\":\"rank\",\"missing_bucket\":true}}}],\"size\":10000}}}}");

        URL url = new URL("https://2e.aonprd.com/Backgrounds.aspx"); // Replace with the desired URL
        url = new URL("https://elasticsearch.aonprd.com/aon/_search?track_total_hits=true");

        int i = 0;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        // Set request headers, if needed
        connection.setRequestProperty("Content-Type", "application/json");

        // Set request body, if needed
        String requestBody = "{\"key\": \"value\"}";

        int intLevel = level != null ? level : 0;

        //requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"query_string\":{\"query\":\"category:background is_general_background:true NOT region:* NOT trait:legacy\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":200,\"sort\":[{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
        if (tradition != null) {
            if (Trait != null && Trait.contains("Cantrip")) {
                requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"term\":{\"trait\":{\"value\":\"cantrip\"}}},{\"query_string\":{\"query\":\"category:spell tradition: " + tradition + "\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":100,\"sort\":[{\"spell_type\":{\"order\":\"asc\"}},{\"rank\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}}],\"size\":10000}},\"group2\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}},{\"field2\":{\"terms\":{\"field\":\"rank\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
            } else {
                requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"range\":{\"level\":{\"gte\":" + intLevel + "}}},{\"range\":{\"level\":{\"lte\":" + intLevel + "}}},{\"query_string\":{\"query\":\"category:spell tradition: " + tradition + "\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"terms\":{\"trait\":[\"cantrip\"]}},{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":100,\"sort\":[{\"spell_type\":{\"order\":\"asc\"}},{\"rank\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}}],\"size\":10000}},\"group2\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}},{\"field2\":{\"terms\":{\"field\":\"rank\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
            }
        } else {
            //String trait = Trait != null && Trait.size() > 0 ? Trait.get(0) : "cantrip";

            String traitsStrings = ""; 

            if (Trait != null) {
                for (String oneTrait: Trait) {
                    traitsStrings += "{\"term\":{\"trait\":{\"value\":\"" + oneTrait + "\"}}},";
                }
            }
            //{"filter":[{"term":{"trait":{"value":"bard"}}},{"term":{"trait":{"value":"focus"}}},
            
            requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[" + traitsStrings + "{\"query_string\":{\"query\":\"category:spell \",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":50,\"sort\":[{\"spell_type\":{\"order\":\"asc\"}},{\"rank\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}}],\"size\":10000}},\"group2\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}},{\"field2\":{\"terms\":{\"field\":\"rank\",\"missing_bucket\":true}}}],\"size\":10000}}}}";

            //requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"term\":{\"trait\":{\"value\":\"" + trait + "\"}}},{\"query_string\":{\"query\":\"category:spell \",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":50,\"sort\":[{\"spell_type\":{\"order\":\"asc\"}},{\"rank\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}}],\"size\":10000}},\"group2\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"spell_type\",\"missing_bucket\":true}}},{\"field2\":{\"terms\":{\"field\":\"rank\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
        }
        //requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"range\":{\"level\":{\"gte\":1}}},{\"range\":{\"level\":{\"lte\":1}}},{\"query_string\":{\"query\":\"category:spell tradition: Occult\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"terms\":{\"trait\":[\"cantrip\"]}},{\"exists\":{\"field\":\"item_child_id\"}},{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":10000,\"sort\":[{\"spell_type\":{\"order\":\"asc\"}},{\"rank\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"track_total_hits\":true,\"_source\":false,\"search_after\":[\"spell\",1,\"mindlink\",27340]}";


        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody.getBytes());
        outputStream.flush();
        outputStream.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;


        while ((line = br.readLine()) != null) {
            i++;
            System.out.println(line);

            //if (line.contains("ctl00_RadDrawer1_Content_MainContent_DetailedOutput")) {
                //System.out.println("Line: " + i);
                //System.out.println(line);

                int id = 0;
                String name = null;
                String description = null;

                Pattern pattern2 = Pattern.compile("spell-(\\d+)\",\"_score\":null,\"sort\":\\[\"([^\"]+)\",(\\d+),\"([^\"]+)\"");
                Matcher matcher2 = pattern2.matcher(line);
                while (matcher2.find()) {
                    id = Integer.parseInt(matcher2.group(1));
                    name = matcher2.group(4);
                    Spell spell = new Spell();
                    spell.setId(id);
                    spell.setName(name);
                    spell.setTradition(tradition);
                    spell.setLevel(Integer.parseInt(matcher2.group(3)));
                    spells.add(spell);                    
                }
            //}
            

        }

        return spells;
    }
}

