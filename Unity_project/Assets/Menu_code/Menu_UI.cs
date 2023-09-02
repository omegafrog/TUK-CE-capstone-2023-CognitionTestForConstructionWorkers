using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using Login;

public class Menu_UI : MonoBehaviour
{
    public GameObject Panel;

    public Text user_ID;

    public Text Maze_count;
    public Text Maze_opt;
    public Text Maze_time;

    public Text Saw_counter;
    public Text Saw_opt;

    public Text Cov_count;
    public Text Cov_opt;
    public Text Cov_avr;


    public Text DMT_Go_missed;
    public Text DMT_AvgResult;
    public Text DMT_opt;

    public Text DST_opt;


    public static bool two_hand_web_result;
    public static bool maze_web_result;
    public static bool conveyor_web_result;
    public static bool DMT_web_result;
    public static bool DST_web_result;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        menu_ID();
        Maze_OP();
        Saw_OP();
        Conveyor_OP();
        DMT_OP();
        DST_OP();
    }

    /// <summary>
    /// ///////////////////////////////////////////////////////////////////////////////////
    /// Maze 기능메뉴
    /// </summary>
    public void Menu_Maze()
    {
        Panel.SetActive(false);
        SceneManager.LoadScene("Maze_test", LoadSceneMode.Single);   
    }
    public void Maze_tuto() {
        Panel.SetActive(false);
        SceneManager.LoadScene("Maze_tuto", LoadSceneMode.Single);
    }

    public void Menu_Saw()
    {
        Panel.SetActive(false);
        SceneManager.LoadScene("Saw_test", LoadSceneMode.Single);
    }
    public void Saw_tuto()
    {
        Panel.SetActive(false);
        SceneManager.LoadScene("Saw_tuto", LoadSceneMode.Single);
    }

    public void Menu_Conveyor()
    {
        Panel.SetActive(false);
        SceneManager.LoadScene("Conveyor_test", LoadSceneMode.Single);
    }

    public void Menu_DMT()
    {
        Panel.SetActive(false);
        SceneManager.LoadScene("DMT_test", LoadSceneMode.Single);
    }
    public void DMT_tuto()
    {
        Panel.SetActive(false);
        SceneManager.LoadScene("DMT_tuto", LoadSceneMode.Single);
    }

    public void Menu_DST()
    {
        Panel.SetActive(false);
        SceneManager.LoadScene("DST_test", LoadSceneMode.Single);
    }
    public void DST_tuto()
    {
        Panel.SetActive(false);
        SceneManager.LoadScene("DST_tuto", LoadSceneMode.Single);
    }

    public void menu_ID()
    {
        user_ID.text ="User: " + LoginManager.ID_save;
    }

    /// <summary>
    ///
    /// 메뉴 결과창
    public void Maze_OP()
    {
        Maze_count.text = "충돌횟수: " + Maze_move.Trigger_Count + "번";
        Maze_time.text = "진행시간: " + Mathf.Round(Maze_UI.Maze_timer) + "초";
        if (Maze_move.maze_result)
        {
            if (Maze_move.Trigger_Count < 2 && Maze_move.Trigger_Count == 0)
            {
                Maze_opt.text = "결과: 통과";
                maze_web_result = true;
            }
            else
            {
                Maze_opt.text = "결과: 실패";
                maze_web_result = false;
            }
        }
        else Maze_opt.text = "결과: 검사 미실시";
    }
    public void Saw_OP()
    {
        Saw_counter.text = "진행시간: " + Mathf.Floor(Move_key.avr_time / 3f * 100f) / 100f + "초";
        if (Move_key.two_hand_result)
        {
            if (Move_key.timeout < 1)
            {
                Saw_opt.text = "결과: 통과";
                two_hand_web_result = true;
            }
            else
            {
                Saw_opt.text = "결과: 실패";
                two_hand_web_result = false;
            }
        }
        else Saw_opt.text = "결과: 검사 미실시";
        
        
    }
    public void Conveyor_OP()
    {
        Cov_count.text = "실패횟수: " + Conveyor_button.err_count + "번";
        //Cov_avr.text = "평균 반응속도: " + Conveyor_button.save_time + "ms";
        if (Conveyor_button.conveyor_result)
        {
            if (Conveyor_button.err_count < 3)
            {
                Cov_opt.text = "결과: 통과";
                conveyor_web_result = true;
            }
            else
            {
                Cov_opt.text = "결과: 실패";
                conveyor_web_result = false;
            }
        }
        else Cov_opt.text = "결과: 검사 미실시";
    }
    public void DMT_OP()
    {

        DMT_Go_missed.text = "놓친 횟수: " + DecisionMakingTest.Go_missed + "번";
        DMT_AvgResult.text = "평균 입력시간 : " + DecisionMakingTest.AvgResult + "초";

        if (DecisionMakingTest.isDMTEnded)
        {
           if (DecisionMakingTest.Avg_GoClickTime <= 600 && DecisionMakingTest.Go_missed <= 2 && DecisionMakingTest.NoGo_clicked <= 2)
            {
                DMT_opt.text = "결과: 통과";
                DMT_web_result = true;
           }
           else
           {
                DMT_opt.text = "결과: 실패";
                DMT_web_result = false;
           }
        }
        else DMT_opt.text = "결과: 검사 미실시";
    }

    public void DST_OP()
    {

        if (DigitSpanGame.DigitGameresult)
        {
            if (DigitSpanGame.isDigitGamePassed)
            {
                DST_opt.text = "결과: 통과";
                DST_web_result = true;
            }
            else
            {
                DST_opt.text = "결과: 실패";
                DST_web_result = false;
            }
        }
        else DST_opt.text = "결과: 검사 미실시";


    }


}
