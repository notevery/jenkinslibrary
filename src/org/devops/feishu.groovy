package org.devops

def sendFeishu(status){
    if (status == "success") {
        message="构建并部署成功，点击查看部署后的效果"
        href_url="${env.ARKIDV2DEV_PUBLIC_URL}"
    } else if(status == "pending"){
        message="开始构建，点击查看构建过程"
        href_url="${env.BUILD_URL}"
    } else {
        message="${this_stage}阶段失败，点击查看具体日志"
        href_url="${env.BUILD_URL}"
    }

    sh """
        curl -X POST \
        ${env.FEISHU_ENTRYPOINT} \
        -H "content-type: application/json" \
        -d \
        '{
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
                                    "text": "$message",
                                    "href": "$href_url"
                                }
                            ]
                        ]
                    }
                }
            }
        }'
    """
}
