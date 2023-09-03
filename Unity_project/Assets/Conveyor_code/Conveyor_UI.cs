using System.Collections;
using System.Collections.Generic;
using System.Threading;
//using System.Web.UI.WebControls;
using UnityEngine;
using UnityEngine.UI;

public class Conveyor_UI : MonoBehaviour
{
    public GameObject Panel;
    public Text countText;
    public Text object_counter;

    public static float playtime;
    public bool timestart;
    public static bool UI_check;

    // Start is called before the first frame update
    void Start()
    {
        playtime = 0;
        Time.timeScale = 0;
        timestart = false;
        UI_check = false;
    }

    // Update is called once per frame
    void Update()
    {
        time_check();
        counter();
    }

    public void game_start()
    {
        Time.timeScale = 1;
        UI_check = true;
        timestart = true;
        Panel.SetActive(false);
        

    }

    public void time_check()
    {
        if(timestart){
            playtime += Time.deltaTime;
            //timeText.text = "제한시간: " + Mathf.Round(playtime) + "초";
        }
    }

    public void counter()
    {
        if (timestart)
        {
            countText.text = "실패: " + Conveyor_button.err_count;
            object_counter.text = "전체: " + Conveyor_move.object_count;
        }
    }
}
