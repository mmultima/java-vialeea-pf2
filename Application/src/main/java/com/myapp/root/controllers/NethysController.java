package com.myapp.root.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

import com.myapp.root.data.CharacterClass;
import com.myapp.root.data.Feat;
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

    @GetMapping(path="/details")
    public String details () throws IOException {
        String value = "";

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
            requestBody = "{\"query\":{\"function_score\":{\"query\":{\"bool\":{\"filter\":[{\"range\":{\"level\":{\"gte\":" + startLevel + "}}},{\"range\":{\"level\":{\"lte\":" + endLevel + "}}},{\"query_string\":{\"query\":\"category:feat trait:(\\\"Sorcerer\\\") NOT trait:kingdom\",\"default_operator\":\"AND\",\"fields\":[\"name\",\"legacy_name\",\"remaster_name\",\"text^0.1\",\"trait_raw\",\"type\"],\"minimum_should_match\":0}},{\"bool\":{\"must_not\":{\"exists\":{\"field\":\"remaster_id\"}}}}],\"must_not\":[{\"term\":{\"exclude_from_search\":true}}]}},\"boost_mode\":\"multiply\",\"functions\":[{\"filter\":{\"terms\":{\"type\":[\"Ancestry\",\"Class\",\"Versatile Heritage\"]}},\"weight\":1.2},{\"filter\":{\"terms\":{\"type\":[\"Trait\"]}},\"weight\":1.05}]}},\"size\":50,\"sort\":[{\"level\":{\"order\":\"asc\"}},{\"name.keyword\":{\"order\":\"asc\"}},\"_doc\"],\"_source\":false,\"aggs\":{\"group1\":{\"composite\":{\"sources\":[{\"field1\":{\"terms\":{\"field\":\"type\",\"missing_bucket\":true}}}],\"size\":10000}}}}";
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
            }
            // Close connection
            connection.disconnect();
            
        


            value = value + responseBody.toString();

        }

        return value;
        //'/Classes.aspx?ID=1
        //='/Feats.aspx?Traits=7
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
                //System.out.println("Line: " + i);
                //System.out.println(line);

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

                gear = new Gear(id2, name, description);
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
}


