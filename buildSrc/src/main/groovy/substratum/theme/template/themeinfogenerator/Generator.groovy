package substratum.theme.template.themeinfogenerator

import groovy.json.*

class Generator {

    static String generateThemeInfo(File location) {

       def themes = location.listFiles().sort().collect { appDir ->

           def type2s = appDir.listFiles()
                   .findAll { it.name.startsWith("type2_") }
                   .collect {it.name - "type2_"}
                   .sort()
                    .collect { [name: it, default: false] }

           def type2Default = appDir.listFiles().find { it.name == "type2" }?.text

           def finalType2 = []

           if (type2s) {
               if (type2Default) {
                   finalType2 = [[name: type2Default, default: true]] + type2s
               } else {
                   finalType2 = [[name: "type2 variant…", default: true]] + type2s
               }
           }

            return [
                    appId: appDir.name,
                    type1: [],
                    type2: finalType2
            ]

        }

        //find{true} so It won't throw IndexOutOfBoundsException. From https://stackoverflow.com/questions/4839834/get-the-first-element-of-a-list-idiomatically-in-groovy
        def type3Default = location.listFiles().sort().find {true}?.listFiles()?.find { it.name == "type3" }?.text

        def type3s = location.listFiles().sort().find {true}?.listFiles()?.findAll {it.name.startsWith("type3_")}?.collect {it.name - "type3_"}?.sort()?.collect {type3 -> [name: type3, default: false] }

        def finalType3s = []

        if (type3s) {
            if (type3Default) {
                finalType3s = [[name: type3Default, default: true]] + type3s
            } else {
                finalType3s = [[name: "type3 variant...", default: true]] + type3s
            }
        }

        return JsonOutput.toJson([
                themes: themes,
                type3: finalType3s
        ])

    }

}