import java.util.ArrayList;
import java.util.List;

public class CrewMemberInfo {
    String memberId;
    String memberName;
    String pirateCrew;
    List<MissionInfo> missions;
    
    public CrewMemberInfo(String memberId, String memberName, String pirateCrew) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.pirateCrew = pirateCrew;
        this.missions = new ArrayList<>();
    }
    
    public void addMission(MissionInfo mission) {
        this.missions.add(mission);
    }
    
    public int getTotalBounty() {
        int total = 0;
        for (MissionInfo m : missions) {
            total += m.bountyReward;
        }
        return total;
    }
}