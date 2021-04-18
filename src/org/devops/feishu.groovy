package org.devops

def FeiShuHTTPRequest(message,href_url,feishuUrl){
      result = httpRequest httpMode: "POST", 
                consoleLogResponseBody: true,
                ignoreSslErrors: true, 
                requestBody: '''{
            "msg_type": "post",
            "content": {
                "post": {
                    "zh_cn": {
                        "title": "流水线状态通知",
                        "content": [
                            [
                                {
                                    "tag": "text",
                                    "text": "${env.REPO_NAME} 项目流水线有更新: "
                                },
                                {
                                    "tag": "a",
                                    "text": message,
                                    "href": href_url
                                }
                            ]
                        ]
                    }
                }
            }
        }''',
                url: feishuUrl
                //quiet: true
    return result
}
