package com.javacreed.examples.sc.part3;

public class Member {

  private final int memberId;
  private final String memberName;

  public Member(final int memberId, final String memberName) {
    this.memberId = memberId;
    this.memberName = memberName;
  }

  public int getMemberId() {
    return memberId;
  }

  public String getMemberName() {
    return memberName;
  }

  @Override
  public String toString() {
    return String.format("[%d] %s", memberId, memberName);
  }
}
