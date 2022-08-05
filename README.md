# Back-end

# 축구팀 매칭 플랫폼 [90분]
<a href = "https://www.ninety.site/">![image](https://user-images.githubusercontent.com/105046055/182977241-f6c3d9f6-981f-4766-8b0e-179fe6776f22.png)
<a href = "https://www.ninety.site/">>서비스 Link<br>
<a href = "https://nasal-peony-253.notion.site/90-64b09693ce5f45bc8b3a14f13f579120">>서비스 소개 notion<br><br><br>

  
## ERD
![image](https://user-images.githubusercontent.com/105046055/182979899-4011ef77-4c27-4be8-8a77-5bf26180280d.png)<br><br><br>

  
## 구현 기능
> 1. 회원가입/로그인
- Spring Security, JWT 인증 방식으로 로그인 구현(refresh token 사용)<br><br>

> 2. 팀 등록/가입
- 새로운 팀을 등록하거나 기존에 있는 팀에 가입
- 팀 등록은 사용자당 1개만 가능
- 팀 가입은 제한 없읍<br><br>
  
> 3. 대결 등록/신청 
- 팀을 등록한 사용자의 경우 대결 등록을 통해 대결 신청을 받을 수 있음
- 대결 등록을 한 상대의 정보를 보고 대결 신청 가능<br><br>
  
> 4. 경기 히스토리(팀, 개인)
- 경기 결과 및 경기에 참여한 인원, 교체 선수, MVP, 분위기 메이커 등등 선정하여 등록 가능
- 등록된 경기 히스토리의 경우 언제든 조회 가능<br><br>
  
> 5. 팀, 개인 정보 상세조회
- 팀 정보 상세 조회시 팀의 전적, 멤버, 경기 히스토리등 해당 팀과 관련된 여러 추가적인 정보 확인 가능
- 개인 정보 상세 조회시 포지션별 점수, 전적, 참여했던 경기 히스토리등 추가적인 정보 확인 가능<br><br>
  
> 6. 랭킹(팀, 개인)
- 팀의 경우 승점과 승률을 기준으로 상위 10개팀을 순위별로 조회
- 개인의 경우 설정한 포지션별로 포지션 점수 상위 10명을 순위별로 조회
- 로그인한 사용자의 개인의 순위와 자신이 참여하고 있는 팀들 순위를 따로 조회 가능<br><br><br>


## 기술 스택
<img src="https://img.shields.io/badge/java-004B8D?style=flat&logo=java&logoColor=white"/> <img src="https://img.shields.io/badge/spring-6DB33F?style=flat&logo=spring&logoColor=white"/> <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=SpringSecurity&logoColor=white"/> <img src="https://img.shields.io/badge/-JWT-black?style=flat&logo=JSON%20Web%20Tokens&logoColor=lightgrey">

<img src="https://img.shields.io/badge/git-F05032?style=flat&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white"/>

<img src="https://img.shields.io/badge/AmazonS3-569A31?style=flat&logo=AmazonS3&logoColor=white"/> <img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=flat&logo=AmazonEC2&logoColor=white"/>
<br><br><br>

  
## 팀원
### Backend(Spring)<br>
- 인상운(팀장) - 팀api<br>
- 문범수 - 회원api, 랭킹api<br>
- 최병민 - 대결api, 경기히스토리api<br><br><br>


## Link
<a href = "https://www.ninety.site/">>서비스 Link<br>
<a href = "https://nasal-peony-253.notion.site/90-64b09693ce5f45bc8b3a14f13f579120">>서비스 소개 notion<br>
<a href = "https://github.com/me-coldrain/FE">>frontend github<br>
