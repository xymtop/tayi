

import {Comment} from "./infoTypes";


//将attributes转为一个comment结构的方法
export function attributesToComment(attributes: any[]): Comment {
    const comment: Comment = {
        senderId: attributes[0].value,
        targetId: attributes[1].value,
        content: attributes[2].value,
        contentType: attributes[3].value,
        time: attributes[4].value,
    };
    return comment;
}

//将comment转为attributes的方法
export function commentToAttributes(comment: Comment): any[] {
    const attributes: any[] = [
        {trait_type: "senderId", value: comment.senderId},
        {trait_type: "targetId", value: comment.targetId},
        {trait_type: "content", value: comment.content},
        {trait_type: "contentType", value: comment.contentType},
        {trait_type: "time", value: comment.time},
    ];
    return attributes;
}
