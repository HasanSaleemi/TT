// insert header here
package assignment6;

import java.util.ArrayList;
import java.util.List;

public class Theater {
	/*
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;
		private String name;

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
			this.name = "";

			int tempRowNum = rowNum + 1;
			while(tempRowNum > 0){
				if (tempRowNum % 26 == 0) {
					this.name = "Z" + this.name;
					tempRowNum = (tempRowNum / 26) - 1;
				} else {
					this.name = (char) ((tempRowNum % 26 - 1) + 65) + this.name;
					tempRowNum/=26;
				}
			}
			this.name = this.name + "" + (seatNum + 1);
		}

		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		@Override
		public String toString() {
			return name;
		}
	}

  	/*
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
	  	private int client;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		@Override
		public String toString() {
			String ret = "";

			String f = "-------------------------------";
			String[] lines = {
					"| Show: " + show,
					"| Box Office ID: " + boxOfficeId,
					"| Seat: " + seat.toString(),
					"| Client: " + client
			};

			for(int i = 0; i < lines.length; i++){
				while(lines[i].length() != f.length() - 1)
					lines[i] = lines[i] + " ";
				lines[i] = lines[i] + "|\n";
			}
			f = f + "\n";

			ret = ret + f;
			for(String s: lines){
				ret = ret + s;
			}
			ret = ret + f;

			return ret;
		}
	}

	private int numRows;
	private int seatsPerRow;
	private String show;

	private int curSeat;
	private ArrayList<Ticket> ticketsSold;

	public Theater(int numRows, int seatsPerRow, String show) {
		this.numRows = numRows;
		this.seatsPerRow = seatsPerRow;
		this.show = show;

		curSeat = 0;
		ticketsSold = new ArrayList<>();
	}

	public int getNumSeats(){
		return curSeat;
	}

	/*
	 * Calculates the best seat not yet reserved
 	 * @return the best seat or null if theater is full
   */
	public Seat bestAvailableSeat() {
		if(curSeat >= numRows * seatsPerRow)
			return null;
		Seat theRet = new Seat(Math.floorDiv(curSeat, seatsPerRow), curSeat - Math.floorDiv(curSeat, seatsPerRow) * seatsPerRow);
		curSeat++;
		return theRet;
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		if(seat == null)
			return null;

		Ticket t = new Ticket(show, boxOfficeId, seat, client);
		ticketsSold.add(t);

		System.out.println(t.toString());

		try {
			Thread.sleep(50);
		} catch (InterruptedException ignored){}

		return t;
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public List<Ticket> getTransactionLog() {
		return ticketsSold;
	}
}
