package substratum.theme.template.themeinfogenerator

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonschema.main.JsonSchemaFactory
import groovy.json.JsonOutput
import spock.lang.Specification

class GeneratorTest extends Specification {

    def schemaFile = new File(getClass().classLoader.getResource("schema.json").path)
    def resources = schemaFile.parentFile
    def schema = JsonSchemaFactory.byDefault().getJsonSchema(new ObjectMapper().readTree(schemaFile))

    def "Very Simple Theme test"() {

        given:
        def location = new File(resources, "VerySimpleTheme")
        def expected = [
                themes: ["android", "com.android.settings", "com.android.systemui"]
                        .collect({app ->
                    return [
                            appId: app,
                            type1: [],
                            type2: []
                    ]
                }),
                type3: []
        ]

        when:
        def theme = Generator.generateThemeInfo(location)
        println(theme)
        def node = new ObjectMapper().readTree(theme)

        then:
        theme == JsonOutput.toJson(expected)
        schema.validate(node).success
    }

    def "Type3 with default test"() {

        given:
        def location = new File(resources, "Type3Test")
        def expected = [
                themes: [
                        [
                                appId: "android",
                                type1: [],
                                type2: []
                        ]
                ],
                type3: [
                        [
                                name: "Light",
                                default: true
                        ],
                        [
                                name: "Black",
                                default: false
                        ],
                        [
                                name: "Dark",
                                default: false
                        ]
                ]
        ]

        when:
        def theme = Generator.generateThemeInfo(location)
        println(theme)
        def node = new ObjectMapper().readTree(theme)

        then:
        theme == JsonOutput.toJson(expected)
        schema.validate(node).success

    }

    def "Type3 without default test"() {

        given:
        def location = new File(resources, "Type3WithoutDefaultTest")
        def expected = [
                themes: [
                        [
                                appId: "android",
                                type1: [],
                                type2: []
                        ]
                ],
                type3: [
                        [
                                name: "type3 variant...",
                                default: true
                        ],
                        [
                                name: "Black",
                                default: false
                        ],
                        [
                                name: "Dark",
                                default: false
                        ]
                ]
        ]

        when:
        def theme = Generator.generateThemeInfo(location)
        println(theme)
        def node = new ObjectMapper().readTree(theme)

        then:
        theme == JsonOutput.toJson(expected)
        schema.validate(node).success

    }

    def "Type2 with and without default test"() {

        given:
        def location = new File(resources, "Type2Test")
        def expected = [
                themes: [
                        [
                                appId: "android",
                                type1: [],
                                type2: [
                                        [
                                                name: "Light",
                                                default: true
                                        ],
                                        [
                                                name: "Black",
                                                default: false
                                        ],
                                        [
                                                name: "Dark",
                                                default: false
                                        ]

                                ]
                        ], [
                                appId: "com.android.settings",
                                type1: [],
                                type2: [
                                        [
                                                name: "type2 variant…",
                                                default: true
                                        ],
                                        [
                                                name: "Green",
                                                default: false
                                        ],
                                        [
                                                name: "Yellow",
                                                default: false
                                        ]
                                ]
                        ]
                ],
                type3: []
        ]

        when:
        def theme = Generator.generateThemeInfo(location)
        println(theme)
        def node = new ObjectMapper().readTree(theme)

        then:
        theme == JsonOutput.toJson(expected)
        schema.validate(node).success

    }

}
