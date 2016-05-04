package com.yabe.util.sql;

import java.util.ArrayList;

public class SQLBuilder {
	enum JoinType{
		JOIN,
		NATURAL_JOIN,
		LEFT_JOIN,
		RIGHT_JOIN
	}
	enum ConditionType{
		AND,
		OR
	}
	class Table{
		JoinType joinType;
		String name;
	}
	class Condition{
		ConditionType type;
		String condition;
	}
	ArrayList<Table> tables = new ArrayList<Table>();
	ArrayList<String> columns = new ArrayList<String>();
	ArrayList<Condition> conditions = new ArrayList<Condition>();
	public SQLBuilder (){
		
	}
	public SQLBuilder select(String column){
		columns.add(column);
		return this;
	}
	
	public SQLBuilder from(String tableName){
		Table table = new Table();
		table.joinType = JoinType.JOIN;
		table.name = tableName;
		tables.add(table);
		return this;
	}
	public SQLBuilder from(JoinType join, String tableName){
		Table table = new Table();
		table.name = tableName;
		table.joinType = join;
		tables.add(table);
		return this;
	}
	
	public SQLBuilder where(ConditionType cond, String condition){
		Condition con = new Condition();
		con.type = cond;
		con.condition=condition;
		conditions.add(con);
		return this;
	}
	
	public String toString(){
		int i;
		String sql ="" ;
		for ( i = 0 ; i < columns.size(); i++ ){
			if (i== 0){
				sql += "SELECT "+ columns.get(i);
			}else{
				sql += ", "+columns.get(i);	
			}
		}
		for  (i = 0 ;i < tables.size(); i++ ){
			if (i== 0){
				sql += " FROM "+ tables.get(i).name;
			}else{
				switch(tables.get(i).joinType){
				case JOIN:
					sql+= ", ";
					break;
				case NATURAL_JOIN:
					sql+= " NATURAL JOIN ";
				case LEFT_JOIN:
					sql+= " LEFT OUTER JOIN ";
				case RIGHT_JOIN:
					sql+=" RIGHT OUTER JOIN ";
				}
				sql += " "+tables.get(i).name;	
			}
		}
		i=0;
		while( i < conditions.size()){
			if (i==0){
				sql += " WHERE " +conditions.get(i).condition;
			}else{
				switch(conditions.get(i).type){
				case AND:
					sql+= " AND (";
					break;
				case OR:
					sql+= " OR (";
					break;
				}
				sql += conditions.get(i).condition +" )";
			}
			i++;
		}
		
		return sql;
	}
	
	
}
