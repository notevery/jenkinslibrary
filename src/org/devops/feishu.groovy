package org.devops

def checkBranchFeishu(status){
    gitBranch = "${env.GIT_BRANCH}"
    gitRepoName = "${env.REPO_NAME}"
    if (status == "success") {
        href_msg="${gitRepoName}-${gitBranch}分支通过单元和风格测试，可以合并, 点击查看"
        repoURL= "${env.REPO_HTTP_URL}"
        cleanRepoURL = repoURL - ".git"
        href_url="${cleanRepoURL}/tree/${gitBranch}"
    } else if(status == "pending"){
        href_msg="${gitRepoName}-${gitBranch}开始执行单元和风格测试，点击查看执行日志"
        href_url="${env.BUILD_URL}"
    } else {
        href_msg="${gitRepoName}-${gitBranch}分支，${this_stage}阶段失败，点击查看具体日志"
        href_url="${env.BUILD_URL}"
    }
    message="${gitRepoName} 项目 ${gitBranch}分支流水线有更新: "
    sendFeishu(href_url, href_msg, message)
}


def devCicdFeishu(status){
    if (status == "success") {
        href_msg="构建并部署成功，点击查看部署后的效果"
        href_url="${env.ARKIDV2DEV_PUBLIC_URL}"
    } else if(status == "pending"){
        href_msg="开始构建，点击查看构建过程"
        href_url="${env.BUILD_URL}"
    } else {
        href_msg="${this_stage}阶段失败，点击查看具体日志"
        href_url="${env.BUILD_URL}"
    }
    message = "${env.REPO_NAME} 项目流水线有更新: "
    sendFeishu(href_url, href_msg, message)
}

def sendFeishu(href_url, href_msg, message){

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
                                    "text": $message
                                },
                                {
                                    "tag": "a",
                                    "text": $href_msg,
                                    "href": $href_url
                                }
                            ]
                        ]
                    }
                }
            }
        }'
    """
}
