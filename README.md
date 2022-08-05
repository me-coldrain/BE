# Back-end

# 📌 축구팀 매칭 플랫폼 [90분]
<a href = "https://www.ninety.site/">![image](https://user-images.githubusercontent.com/105046055/182977241-f6c3d9f6-981f-4766-8b0e-179fe6776f22.png)
<a href = "https://www.ninety.site/">> 사이트 바로가기<br>
<a href = "https://nasal-peony-253.notion.site/90-64b09693ce5f45bc8b3a14f13f579120">> 서비스 소개 바로가기<br>
<a href = "https://youtu.be/QC6GeZKvQvI">> 발표 영상 바로가기<br>
<a href = "https://github.com/me-coldrain/FE">> 프론트 엔드 Github 바로가기<br>
<a href = "https://undefined-374.gitbook.io/api-docs/">> API 명세서 바로가기(Gitbook)<br><br><br>

## ⏱ 프로젝트 기간
> 22.06.24 - 22.08.05 (6주)

 <br/>
 
## 👥 BACKEND MEMBERS
- **인상운** 🔰 
  - 팀 API <br/><br/>
 
- **문범수** 
  - 회원 API
  - 랭킹 API <br/><br/>
  
- **최병민** 
  - 대결 API
  - 경기 API <br/><br/>
<br/>
 
## 🔀 ERD(Entity Relationship Diagram)
![image](https://user-images.githubusercontent.com/105046055/182979899-4011ef77-4c27-4be8-8a77-5bf26180280d.png)<br><br><br>
  
## ✨ SERVICE ARCHITECTURE
![image](https://user-images.githubusercontent.com/81298415/182985511-bd0cdf16-6299-4b03-8d95-83372ef614ea.png)
<br><br><br>
## 🏹 SKILLS
### - PLATFORMS & LANGUAGE 
![Spring](https://img.shields.io/badge/Spring-6DB33F.svg?&style=for-the-badge&logo=Spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?&style=for-the-badge&logo=Spring%20Boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F.svg?&style=for-the-badge&logo=Spring%20Security&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545.svg?&style=for-the-badge&logo=MariaDB&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-F8DC75.svg?&style=for-the-badge&logo=Apache%20Tomcat&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon%20EC2-FF9900.svg?&style=for-the-badge&logo=Amazon%20EC2&logoColor=white)
![Amazon S3](https://img.shields.io/badge/Amazon%20S3-569A31.svg?&style=for-the-badge&logo=Amazon%20S3&logoColor=white)
![Amazon RDS](https://img.shields.io/badge/Amazon%20RDS-527FFF.svg?&style=for-the-badge&logo=Amazon%20RDS&logoColor=white)

### - COLLABORATION & TOOLS 
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-0071C5.svg?&style=for-the-badge&logo=IntelliJ%20IDEA&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032.svg?&style=for-the-badge&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717.svg?&style=for-the-badge&logo=GitHub&logoColor=white)
![GitBook](https://img.shields.io/badge/GitBook-3884FF.svg?&style=for-the-badge&logo=GitBook&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B.svg?&style=for-the-badge&logo=Slack&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E.svg?&style=for-the-badge&logo=Figma&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000.svg?&style=for-the-badge&logo=Notion&logoColor=white)
<br><br><br>
  
## 🛰️ FEATURES
> 1. 회원가입/로그인
- Spring Security, JWT 인증 방식으로 로그인 구현<br><br>

> 2. 팀 등록/가입/팀원 모집
- 새로운 팀을 등록하거나 기존에 있는 팀에 가입
- 팀 등록은 사용자당 1개 팀만 생성 가능
- 팀 가입의 경우 제한 없음
- 팀 목록 조회, 대결 목록 조회 [지역/요일/시간/승률/팀 이름] 동적 필터링<br><br>
  
> 3. 대결 등록/수락/신청
- 팀을 생성한 사용자의 경우 대결 등록을 통해 대결 신청을 받을 수 있음
- 대결등록을 하고 신청이 들어온 상대의 연락처를 통해 경기 일정 조정
- 대결 등록을 한 상대의 정보를 보고 대결 신청 가능<br><br>
  
> 4. 경기 종료/경기 결과/ 경기 인원/ 경기 히스토리(팀, 개인)
- 경기 종료 시 경기결과 입력 후 상대팀에게 해당 결과 확인
- 경기결과 확인 후 각 팀은 해당 경기에 참여한 인원 및 MVP등 추가적인 경기결과 입력
- 경기 결과 및 경기에 참여한 인원, 교체 선수, MVP, 분위기 메이커 등등 선정하여 등록 가능
- 등록된 경기 히스토리의 경우 언제든 조회 가능<br><br>
  
> 5. 팀 정보 조회/ 팀 경기 히스토리/ 개인 경기 히스토리/ 개인 정보 조회
- 팀 정보 상세 조회시 팀의 전적, 멤버, 경기 히스토리등 해당 팀과 관련된 여러 추가적인 정보 확인 가능
- 개인 정보 상세 조회시 포지션별 점수, 전적, 참여했던 경기 히스토리등 추가적인 정보 확인 가능<br><br>
  
> 6. 팀 랭킹 조회/선수 랭킹 조회/ 개인 랭킹
- 팀의 경우 승점과 승률을 기준으로 상위 10개팀을 순위별로 조회
- 개인의 경우 설정한 포지션별로 포지션 점수 상위 10명을 순위별로 조회
- 로그인한 사용자의 개인의 순위와 자신이 참여하고 있는 팀들 순위를 따로 조회 가능<br><br><br>
 
 
## TROUBLE SHOOTING

