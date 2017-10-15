### ThemePack JSON scheme

```
{
    "$schema": "http://json-schema.org/draft-06/schema#",
    "title": "ThemePack",
    "type": "object"
    "properties": {
        "themes": {
            "type": "array",
            "items": {
                "title": "Theme",
                "type": "object",
                "properties": {
                }
            },
            "minItems": 0
        },
        "type3": {
            "type": "array",
            "items": {
                "title": "Type3 Extension"
                "type": "object",
                "properties": {
                    "name": {
                        "type": "string"
                    },
                    "default": {
                        "type": "boolean"
                    },
                }
            },
            "minItems": 0
        },
    },
    "required": ["themes", "type3"]
}
```