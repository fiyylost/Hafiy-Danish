public class CompletedMission {
    public MissionInfo mission;
    public String memberId;
    public String memberName;
    public String pirateCrew;
    
    public CompletedMission(MissionInfo mission, String memberId, String memberName, String pirateCrew) {
        this.mission = mission;
        this.memberId = memberId;
        this.memberName = memberName;
        this.pirateCrew = pirateCrew;
    }
}