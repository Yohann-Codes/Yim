package test;

import account.login.Login;
import account.logout.Logout;
import account.person.AllFriend;
import account.person.FriendInfo;
import account.person.InfoLook;
import account.person.InfoUpdate;
import account.register.Register;
import common.UserInfo;
import friends.FriendAdd;
import friends.FriendRemove;
import friends.FriendReply;
import future.*;
import groups.*;
import message.person.PersonMsg;

import java.util.*;

/**
 * 简单的测试Client
 * <p>
 * Created by yohann on 2017/1/15.
 */
public class Client {
    public static String[] cArr;

    public static void main(String[] args) {
        // 注册消息接收器
        Future.getFuture().addReceiver(new MyReceiver());

        Map<String, Integer> cMap = new HashMap<String, Integer>();
        cMap.put("login", 1);
        cMap.put("logout", 2);
        cMap.put("register", 3);
        cMap.put("send", 4);
        cMap.put("add", 5);
        cMap.put("reply", 6);
        cMap.put("remove", 7);
        cMap.put("set", 8);
        cMap.put("show", 9);
        cMap.put("friend", 10);
        cMap.put("create", 11);
        cMap.put("disband", 12);
        cMap.put("invite", 13);
        cMap.put("kick", 14);
        cMap.put("group", 15);

        Scanner sc = new Scanner(System.in);
        while (true) {
            if (sc.hasNextLine()) {
                String command = sc.nextLine();
                cArr = command.split(" ");
                switch (cMap.get(cArr[0])) {
                    case 1:
                        login();
                        break;
                    case 2:
                        new Logout().execute();
                        break;
                    case 3:
                        register();
                        break;
                    case 4:
                        sendPersonMsg();
                        break;
                    case 5:
                        addFriend();
                        break;
                    case 6:
                        friendReply();
                        break;
                    case 7:
                        removeFriend();
                        break;
                    case 8:
                        setInfo();
                        break;
                    case 9:
                        showInfo();
                        break;
                    case 10:
                        allFriends();
                        break;
                    case 11:
                        createGroup();
                        break;
                    case 12:
                        disbandGroup();
                        break;
                    case 13:
                        invite();
                        break;
                    case 14:
                        kick();
                        break;
                    case 15:
                        myGroups();
                        break;
                }
            }
        }
    }

    private static void myGroups() {
        Future future = new AllGroups(UserInfo.username).execute();
        future.addListener(new AllGroupsFutureListener() {
            public void onReceiveAllGroups(Map<String, List<String>> groups) {
                if (groups.size() != 0) {
                    Set<Map.Entry<String, List<String>>> entries = groups.entrySet();
                    Iterator<Map.Entry<String, List<String>>> ite = entries.iterator();
                    while (ite.hasNext()) {
                        Map.Entry<String, List<String>> group = ite.next();
                        String groupName = group.getKey();
                        List<String> members = group.getValue();
                        System.out.println(groupName + " " + members);
                    }
                } else {
                    System.out.println("没有加入任何讨论组");
                }
            }
        });
    }

    private static void kick() {
        Future future = new MemberKick(UserInfo.username, cArr[1], cArr[2]).execute();
        future.addListener(new MemberKickFutureListener() {
            public void onSuccess(String groupName, String member) {
                System.out.println(member + " 已被踢出讨论组<" + groupName + ">");
            }

            public void onFailure(String hint) {
                System.out.println("踢出成员失败，错误提示：" + hint);
            }
        });
    }

    private static void invite() {
        Future future = new MemberInvite(UserInfo.username, cArr[1], cArr[2]).execute();
        future.addListener(new MemberInviteFutureListener() {
            public void onSuccess() {
                System.out.println("邀请成功");
            }

            public void onFailure(String hint) {
                System.out.println("邀请失败，错误提示：" + hint);
            }
        });
    }

    private static void disbandGroup() {
        Future future = new GroupDisband(UserInfo.username, cArr[1]).execute();
        future.addListener(new GroupDisbandFutureListener() {
            public void onSuccess(String groupName) {
                System.out.println("讨论组<" + groupName + "> 已解散");
            }

            public void onFailure(String hint) {
                System.out.println("讨论组解散失败，错误提示：" + hint);
            }
        });
    }

    private static void createGroup() {
        Future future = new GroupCreate(UserInfo.username, cArr[1]).execute();
        future.addListener(new GroupCreateFutureListener() {
            public void onSuccess(String groupName) {
                System.out.println("讨论组<" + groupName + "> 创建成功");
            }

            public void onFailure(String hint) {
                System.out.println("讨论组创建失败，错误提示：" + hint);
            }
        });
    }

    private static void allFriends() {
        Future future = new AllFriend(UserInfo.username).execute();
        future.addListener(new AllFriendFutureListener() {
            public void onExist(Map<String, Boolean> friends) {
                Set<Map.Entry<String, Boolean>> entries = friends.entrySet();
                Iterator<Map.Entry<String, Boolean>> ite = entries.iterator();
                while (ite.hasNext()) {
                    Map.Entry<String, Boolean> entry = ite.next();
                    String username = entry.getKey();
                    Boolean isOnline = entry.getValue();
                    if (isOnline) {
                        System.out.println(username + "  在线");
                    } else {
                        System.out.println(username + "  离线");
                    }
                }
            }

            public void onNoExist() {
                System.out.println("没有添加好友");
            }
        });
    }

    private static void showInfo() {
        if (cArr[1].equals("me")) {
            Future future = new InfoLook(UserInfo.username).execute();
            future.addListener(new InfoLookFutureListener() {
                public void onSuccess(String username, String name, String sex, String age,
                                      String phone, String address, String introduction) {
                    System.out.println("用户名：" + username);
                    System.out.println("姓名：" + name);
                    System.out.println("性别：" + sex);
                    System.out.println("年龄：" + age);
                    System.out.println("联系电话：" + phone);
                    System.out.println("家庭住址：" + address);
                    System.out.println("个人介绍：" + introduction);
                }

                public void onFailure(String hint) {
                    System.out.println("个人信息查询失败，错误提示：" + hint);
                }
            });
        }
        if (cArr[1].equals("friend")) {
            Future future = new FriendInfo(UserInfo.username, cArr[2]).execute();
            future.addListener(new FriendInfoFutureListener() {
                public void onSuccess(String username, String name, String sex, String age,
                                      String phone, String address, String introduction) {

                    System.out.println("用户名：" + username);
                    System.out.println("姓名：" + name);
                    System.out.println("性别：" + sex);
                    System.out.println("年龄：" + age);
                    System.out.println("联系电话：" + phone);
                    System.out.println("家庭住址：" + address);
                    System.out.println("个人介绍：" + introduction);
                }

                public void onFailure(String hint) {
                    System.out.println("好友信息查询失败，错误提示：" + hint);
                }
            });
        }
    }

    private static void setInfo() {
        InfoUpdate infoUpdate = new InfoUpdate(UserInfo.username);
        if (cArr[1].equals("password")) {
            infoUpdate.setPassword(cArr[2]);
            Future future = infoUpdate.execute();
            future.addListener(new InfoUpdateFutureListener() {
                public void onSuccess() {
                    System.out.println("密码修改成功");
                }

                public void onFailure(String hint) {
                    System.out.println("密码修改失败，错误提示：" + hint);
                }
            });
        }
        if (cArr[1].equals("name")) {
            infoUpdate.setName(cArr[2]);
            Future future = infoUpdate.execute();
            future.addListener(new InfoUpdateFutureListener() {
                public void onSuccess() {
                    System.out.println("姓名修改成功");
                }

                public void onFailure(String hint) {
                    System.out.println("姓名修改失败，错误提示：" + hint);
                }
            });
        }
        if (cArr[1].equals("sex")) {
            infoUpdate.setSex(cArr[2]);
            Future future = infoUpdate.execute();
            future.addListener(new InfoUpdateFutureListener() {
                public void onSuccess() {
                    System.out.println("性别修改成功");
                }

                public void onFailure(String hint) {
                    System.out.println("性别修改失败，错误提示：" + hint);
                }
            });
        }
        if (cArr[1].equals("age")) {
            infoUpdate.setAge(cArr[2]);
            Future future = infoUpdate.execute();
            future.addListener(new InfoUpdateFutureListener() {
                public void onSuccess() {
                    System.out.println("年龄修改成功");
                }

                public void onFailure(String hint) {
                    System.out.println("年龄修改失败，错误提示：" + hint);
                }
            });
        }
        if (cArr[1].equals("phone")) {
            infoUpdate.setPhone(cArr[2]);
            Future future = infoUpdate.execute();
            future.addListener(new InfoUpdateFutureListener() {
                public void onSuccess() {
                    System.out.println("电话修改成功");
                }

                public void onFailure(String hint) {
                    System.out.println("电话修改失败，错误提示：" + hint);
                }
            });
        }
        if (cArr[1].equals("address")) {
            infoUpdate.setAddress(cArr[2]);
            Future future = infoUpdate.execute();
            future.addListener(new InfoUpdateFutureListener() {
                public void onSuccess() {
                    System.out.println("地址修改成功");
                }

                public void onFailure(String hint) {
                    System.out.println("地址修改失败，错误提示：" + hint);
                }
            });
        }
        if (cArr[1].equals("introduction")) {
            infoUpdate.setIntroduction(cArr[2]);
            Future future = infoUpdate.execute();
            future.addListener(new InfoUpdateFutureListener() {
                public void onSuccess() {
                    System.out.println("个人介绍修改成功");
                }

                public void onFailure(String hint) {
                    System.out.println("个人介绍修改失败，错误提示：" + hint);
                }
            });
        }
    }

    private static void removeFriend() {
        Future future = new FriendRemove(UserInfo.username, cArr[1]).execute();
        future.addListener(new FriendRemoveFutureListener() {
            public void onSuccess() {
                System.out.println("删除好友成功");
            }

            public void onFailure(String hint) {
                System.out.println("删除好友失败，错误提示：" + hint);
            }
        });
    }

    private static void friendReply() {
        Future future = null;
        if (cArr[2].equals("yes")) {
            future = new FriendReply(UserInfo.username, cArr[1], true).execute();
        }
        if (cArr[2].equals("no")) {
            future = new FriendReply(UserInfo.username, cArr[1], false).execute();
        }
        future.addListener(new FriendReplyFutureListener() {
            public void onSuccess() {
                System.out.println("发送成功");
            }

            public void onFailure(String hint) {
                System.out.println("发送失败，错误提示：" + hint);
            }
        });
    }

    private static void addFriend() {
        Future future = new FriendAdd(UserInfo.username, cArr[1], cArr[2]).execute();
        future.addListener(new FriendAddFutureListener() {
            public void onSuccess() {
                System.out.println("发送成功，等待对方处理");
            }

            public void onFailure(String hint) {
                System.out.println("发送失败，错误提示：" + hint);
            }
        });
    }

    private static void sendPersonMsg() {
        Future future = new PersonMsg(cArr[1], cArr[2]).execute();
        future.addListener(new PersonMsgFutureListener() {
            public void onSuccess() {
                System.out.println("发送成功");
            }

            public void onFailure(String hint) {
                System.out.println("发送失败，错误提示：" + hint);
            }
        });
    }

    private static void register() {
        Future future = new Register(cArr[1], cArr[2]).execute();
        future.addListener(new RegisterFutureListener() {
            public void onSuccess() {
                System.out.println("注册成功");
            }

            public void onFailure(String hint) {
                System.out.println("注册失败，错误提示：" + hint);
            }
        });
    }

    public static void login() {
        Future future = new Login(cArr[1], cArr[2]).execute();
        future.addListener(new LoginFutureListener() {
            public void onSuccess() {
                System.out.println("登录成功");
            }

            public void onFailure(String hint) {
                System.out.println("登录失败，错误提示：" + hint);
            }
        });
    }
}
