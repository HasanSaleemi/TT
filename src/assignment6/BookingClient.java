// Insert header here
package assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;

public class BookingClient {
    private Theater theater;
    private Map<String, Integer> office;
  /*
   * @param office maps box office id to number of customers in line
   * @param theater the theater where the show is playing
   */
  public BookingClient(Map<String, Integer> office, Theater theater) {
    this.theater = theater;
    this.office = office;
  }

  private boolean hasSoldOut = false;
  private synchronized void printSoldOut(){
      if(!hasSoldOut){
          System.out.println("Sorry, we are sold out!");
          hasSoldOut = true;
      }
  }

  /*
   * Starts the box office simulation by creating (and starting) threads
   * for each box office to sell tickets for the given theater
   *
   * @return list of threads used in the simulation,
   *         should have as many threads as there are box offices
   */
    private ReentrantLock lock = new ReentrantLock(true);
	public List<Thread> simulate() {
        ArrayList<Thread> threads = new ArrayList<>();

		for(String boxOffice : office.keySet()){
            threads.add(new Thread(() -> {
                while(office.get(boxOffice) > 0){
                    lock.lock();
                    Theater.Seat best = theater.bestAvailableSeat();
                    if(best != null){
                        theater.printTicket(boxOffice, best, theater.getNumSeats());
                        lock.unlock();
                        office.put(boxOffice, office.get(boxOffice) - 1);
                    }else{
                        lock.unlock();
                        break;
                    }
                }
                if(office.get(boxOffice) > 0)
                    printSoldOut();
            }));
        }
        for(Thread t : threads)
            t.start();

        return threads;
	}

	public static void main(String[] args){
        Map<String, Integer> testOff = new HashMap<>();
        testOff.put("BX1", 3);
        testOff.put("BX2", 4);
        testOff.put("BX3", 3);
        testOff.put("BX4", 3);
        testOff.put("BX5", 3);
        Theater testThe = new Theater(3, 5, "Ouija");

        BookingClient test = new BookingClient(testOff, testThe);
        test.simulate();
    }
}

