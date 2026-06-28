
                System.out.printf("%-10d %-10s %-10s %-10d %-10s %n", rs.getInt("id"), rs.getString("action"),
                        rs.getString("affected_table"), rs.getInt("affected_id"), rs.getDate("performed_at"));
            