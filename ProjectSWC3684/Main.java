import javax.swing.*;
import java.awt.Font;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        LinkedList<CrewMemberInfo> crewList = new LinkedList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("onepiece_combined.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 9) continue;

                String memberId = parts[0];
                String memberName = parts[1];
                String pirateCrew = parts[2];
                String missionId = parts[3];
                String missionType = parts[4];
                int dangerLevel = Integer.parseInt(parts[5]);
                String missionDate = parts[6];
                String expectedDuration = parts[7];
                int bountyReward = Integer.parseInt(parts[8]);

                MissionInfo mission = new MissionInfo(missionId, missionType, dangerLevel, missionDate, expectedDuration, bountyReward);

                CrewMemberInfo existing = findCrewById(crewList, memberId);
                if (existing == null) {
                    CrewMemberInfo newCrew = new CrewMemberInfo(memberId, memberName, pirateCrew);
                    newCrew.addMission(mission);
                    crewList.add(newCrew);
                } else {
                    existing.addMission(mission);
                }
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not found !");
            return;
        }

        // --------- Phase 1 : DATA MODELING ( LINKED LIST ) ----------- 
        
        StringBuilder sb = new StringBuilder();
        for (CrewMemberInfo crew : crewList) {
            sb.append("ID : ").append(crew.memberId)
              .append("  | Name : ").append(crew.memberName)
              .append("  | Crew : ").append(crew.pirateCrew).append("\n");

            for (MissionInfo m : crew.missions) {
                sb.append("  - ").append(m.missionId)
                  .append("  | Mission Type : ").append(m.missionType)
                  .append("  | Danger level : ").append(m.dangerLevel)
                  .append("  | Date : ").append(m.missionDate)
                  .append("  | Duration : ").append(m.expectedDuration)
                  .append("  | Bounty Reward : ").append(m.bountyReward).append("\n");
            }

            sb.append("Total Bounty : ").append(crew.getTotalBounty()).append("\n\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(1200, 700));
        JOptionPane.showMessageDialog(null, scrollPane, "List of pirate missions", JOptionPane.INFORMATION_MESSAGE);
        
        // --------- Phase 2 : MISSION ROUTING ( QUEUE ) -----------
        
        Queue<CrewMemberInfo> eastBlueQueue = new LinkedList<>();
        Queue<CrewMemberInfo> southBlueQueue = new LinkedList<>();
        Queue<CrewMemberInfo> grandLineQueue = new LinkedList<>();
        
        boolean toggle = true;
        
        for (CrewMemberInfo crew : crewList) {
            if (crew.missions.size() <= 3) {
                if (toggle) {
                    eastBlueQueue.add(crew);
                } else {
                    southBlueQueue.add(crew);
                }
                toggle = !toggle;
            } else {
                grandLineQueue.add(crew);
            }
        }
        
        StringBuilder sb2 = new StringBuilder();
        
        sb2.append("=== EAST BLUE ROUTE ===\n");
        if (eastBlueQueue.isEmpty()) {
            sb2.append("(Dont have crew in this route)\n\n");
        } else {
            for (CrewMemberInfo crew : eastBlueQueue) {
            sb2.append("ID : ").append(crew.memberId)
            .append("  | Name : ").append(crew.memberName)
            .append("  | Crew : ").append(crew.pirateCrew).append("\n");
            
            for (MissionInfo m : crew.missions) {
                sb2.append(" - ").append(m.missionId)
                .append("  | Mission Type : ").append(m.missionType)
                .append("  | Bounty Reward : ").append(m.bountyReward).append("\n");
            }
            sb2.append("Total Bounty : ").append(crew.getTotalBounty()).append("\n\n");
          }
        }
        
        
        sb2.append("=== SOUTH BLUE ROUTE ===\n");
        if (southBlueQueue.isEmpty()) {
            sb2.append("(Dont have crew in this route)\n\n");
        } else {
            for (CrewMemberInfo crew : southBlueQueue) {
            sb2.append("ID : ").append(crew.memberId)
            .append("  | Name : ").append(crew.memberName)
            .append("  | Crew : ").append(crew.pirateCrew).append("\n");
            
            for (MissionInfo m : crew.missions) {
                sb2.append(" - ").append(m.missionId)
                .append("  | Mission Type : ").append(m.missionType)
                .append("  | Bounty Reward : ").append(m.bountyReward).append("\n");
            }
            sb2.append("Total Bounty : ").append(crew.getTotalBounty()).append("\n\n");
          }
        }
        
        sb2.append("=== GRAND LINE ROUTE ===\n");
        if (grandLineQueue.isEmpty()) {
            sb2.append("(Dont have crew in this route)\n\n");
        } else {
            for (CrewMemberInfo crew : grandLineQueue) {
            sb2.append("ID : ").append(crew.memberId)
            .append("  | Name : ").append(crew.memberName)
            .append("  | Crew : ").append(crew.pirateCrew).append("\n");
            
            for (MissionInfo m : crew.missions) {
                sb2.append(" - ").append(m.missionId)
                .append("  | Mission Type : ").append(m.missionType)
                .append("  | Bounty Reward : ").append(m.bountyReward).append("\n");
            }
            sb2.append("Total Bounty : ").append(crew.getTotalBounty()).append("\n\n");
          }
        }
        
        JTextArea textArea2 = new JTextArea(sb2.toString());
        textArea2.setEditable(false);
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);
        textArea2.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        scrollPane2.setPreferredSize(new java.awt.Dimension(1200, 700));
        
        JOptionPane.showMessageDialog(null, scrollPane2, "Mission Routing", JOptionPane.INFORMATION_MESSAGE);
        
        // --------- Phase 3 : MISSION COMPLETION ( STACK ) -----------
        
        Stack<CompletedMission> completedMissions = new Stack<>();
        
        for (CrewMemberInfo crew : eastBlueQueue) {
            for (MissionInfo m : crew.missions) {
                completedMissions.push(new CompletedMission(m, crew.memberId, crew.memberName, crew.pirateCrew));
            }
        }
        for (CrewMemberInfo crew : southBlueQueue) {
            for (MissionInfo m : crew.missions) {
                completedMissions.push(new CompletedMission(m, crew.memberId, crew.memberName, crew.pirateCrew));
            }
        }
        for (CrewMemberInfo crew : grandLineQueue) {
            for (MissionInfo m : crew.missions) {
                completedMissions.push(new CompletedMission(m, crew.memberId, crew.memberName, crew.pirateCrew));
            }
        }
        
        StringBuilder sb3 = new StringBuilder();
        sb3.append("=== COMPLETED MISSIONS ( LIFO Order ) ===\n\n");
        
        if (completedMissions.isEmpty()) {
            sb3.append("Dont have mission completed.\n");
        } else {
            while (!completedMissions.isEmpty()) {
                CompletedMission cm = completedMissions.pop();
                MissionInfo m = cm.mission;
                
                sb3.append("ID : ").append(cm.memberId)
                   .append("  | Name : ").append(cm.memberName)
                   .append("  | Crew : ").append(cm.pirateCrew).append("\n")
                   .append("  - ").append(m.missionId)
                   .append("  | ").append(m.missionType)
                   .append("  | Danger : ").append(m.dangerLevel)
                   .append("  | Date : ").append(m.missionDate)
                   .append("  | Duration :").append(m.expectedDuration)
                   .append("  | Total Bounty Reward Earned :").append(m.bountyReward)
                   .append("\n");
                   
            }
        }
        
        JTextArea textArea3 = new JTextArea(sb3.toString());
        textArea3.setEditable(false);
        textArea3.setLineWrap(true);
        textArea3.setWrapStyleWord(true);
        textArea3.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        JScrollPane scrollPane3 = new JScrollPane(textArea3);
        scrollPane3.setPreferredSize(new java.awt.Dimension(1200, 700));
        
        JOptionPane.showMessageDialog(null, scrollPane3, "Mission completion", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static CrewMemberInfo findCrewById(LinkedList<CrewMemberInfo> list, String id) {
        for (CrewMemberInfo c : list) {
            if (c.memberId.equals(id)) return c;
        }
        return null;
    }
}
