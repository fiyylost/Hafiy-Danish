public class MissionInfo {
    String missionId;
    String missionType;
    int dangerLevel;
    String missionDate;
    String expectedDuration;
    int bountyReward;
    
    public MissionInfo(String missionId, String missionType, int dangerLevel, String missionDate, String expectedDuration, int bountyReward) {
        this.missionId = missionId;
        this.missionType = missionType;
        this.dangerLevel = dangerLevel;
        this.missionDate = missionDate;
        this.expectedDuration = expectedDuration;
        this.bountyReward = bountyReward;
    }
}