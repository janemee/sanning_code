//package com.huimi.apis.controller;
//
//public class Test {
//    public ListNode swapPairs(ListNode head) {
//        if(head==null)
//            return null;
//        if(head.next==null){
//            return head;
//        }
//        ListNode newHead = head.next;
//        while(head.next!=null){
//            ListNode nextNode = head.next;
//            if(nextNode.next==null){
//                head.next = null;
//            }else{
//                if(nextNode.next.next==null)
//                    head.next = nextNode.next;
//                else
//                    head.next = nextNode.next.next;
//            }
//            ListNode temp = head;
//            head = nextNode.next;
//            nextNode.next = temp;
//            if(head==null){
//                break;
//            }
//        }
//        return newHead;
//    }
//}
