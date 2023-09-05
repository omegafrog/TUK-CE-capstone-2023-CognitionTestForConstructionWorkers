using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;


public class DecisionMakingTest_tuto : MonoBehaviour
{
    private GameObject PracticeGamestartPannel;
    private Button PracticeGamestartbutton;

    string PracticeGoList, PracticeGoNoGoQuestion;//문제 담는 변수

    private GameObject PracticeQuestionDisplayGameobject;//사용자가 입력한 숫자를 유니티 화면에 표시해주는 역할
    private TextMesh PracticeQuestionDisplay; // 사용자가 입력한 숫자를 유니티 화면에 표시해줄 텍스트를 저장할 TextMesh 컴포넌트   

    public static bool isDMTstarted;

    public static int Go_clicked, Go_missed, NoGo_clicked; //버튼 클릭 판별
    public static double Sum_GoClickTime, Avg_GoClickTime;//계산 결과 담을 변수

    private string AvgResult;

    private int ProcessCounting;
    private float startTime;      // 시간 측정 시작 시간
    private float buttonTime;     // 버튼이 눌린 시간
    private float elapsedTime;    // 경과 시간
    public static bool buttonPressed;   // 버튼이 눌렸는지 여부

    public Text DMT_Go_missed;
    public Text DMT_NoGo_clicked;
    public Text DMT_AvgResult;
    public Text DMT_ProcessCounting;

    // Start is called before the first frame update
    void Start()
    {
        Go_clicked = 0;
        Go_missed = 0;
        NoGo_clicked = 0;
        Sum_GoClickTime = 0;
        Avg_GoClickTime = 0;
        ProcessCounting = 0;

        isDMTstarted = false;

        PracticeGamestartPannel = GameObject.FindGameObjectWithTag("DMT_PracticeGamestartPanel");
        PracticeGamestartbutton = GameObject.FindGameObjectWithTag("DMT_PracticeGamestartButton").GetComponent<Button>();
        PracticeQuestionDisplay = GameObject.FindGameObjectWithTag("DMT_PracticeQuestionText").GetComponent<TextMesh>();
        PracticeQuestionDisplay.text = "+";


        PracticeGoList = "ㄹ";
        PracticeGoNoGoQuestion = "ㄹㅓ4";

        PracticeGamestartbutton.onClick.AddListener(PracticeButtonClick);//게임시작

    }


    // Update is called once per frame
    void Update()
    {
        ShowScore();
        ButtonClick();
    }


    private void PracticeButtonClick()
    {
        isDMTstarted = true;
        PracticeGamestartbutton.gameObject.SetActive(false);
        PracticeGamestartPannel.SetActive(false);
        StartCoroutine(PracticeGoNoGoGame());

    }
    public void ButtonClick()//버튼 클릭할 수 있게 하는 함수
    {
        if (Input.GetMouseButtonDown(0)) // 마우스 클릭 시
        {
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;

            if (Physics.Raycast(ray, out hit))
            {
                if (hit.collider.CompareTag("DMT_PracticeInputButton")) //이 이름의 태그를 가진 버튼 오브젝트를 눌렀을 때
                {
                    buttonPressed = true;
                    buttonTime = Time.time;

                }
            }
        }
    }


    IEnumerator PracticeGoNoGoGame()
    {

        PracticeQuestionDisplay.text = '+'.ToString();
        yield return new WaitForSeconds(1.5f); // 1.5초 동안 처음 준비 화면 보여줌

        foreach (char q in PracticeGoNoGoQuestion)
        {
            ProcessCounting++;
            buttonPressed = false;
            ///Go 에서는 누른 시간, 놓친 여부 측정
            if (PracticeGoList.Contains(q.ToString()))//눌러야 할 때
            {

                PracticeQuestionDisplay.text = q.ToString();
                yield return new WaitForSeconds(0.3f); // 0.3초 동안 문제 보여줌
                PracticeQuestionDisplay.text = "";//문제 사라짐
                startTime = Time.time;
                yield return new WaitForSeconds(1.8f); // 1.8초 동안 입력 대기

                if (buttonPressed == true)
                {
                    Go_clicked++;
                    elapsedTime = buttonTime - startTime;
                    Sum_GoClickTime += elapsedTime;
                }
                else
                {
                    //Go인데 못누른 카운트 추가
                    ++Go_missed;
                }

                yield return new WaitForSeconds(0.5f); // 0.5초 동안 휴식
            }
            else//누르지 말아야 할 때
            {
                PracticeQuestionDisplay.text = q.ToString();
                yield return new WaitForSeconds(0.3f); // 0.3초 동안 문제 보여줌
                PracticeQuestionDisplay.text = "";//문제 사라짐
                yield return new WaitForSeconds(1.8f); // 1.8초 동안 입력 대기

                if (buttonPressed == true)
                {
                    ++NoGo_clicked;
                }

                yield return new WaitForSeconds(0.5f); // 0.5초 동안 대기
            }
        }
        PracticeQuestionDisplay.text = "";//대기 화면 보여줌
        Counting();
        yield break;
    }


    /// <summary>
    /// 버튼이 눌리면 Go, NoGo 2가지로 나눠서 해야 함. 
    /// Go 일때는 누르면 시간 체크, 못누르면 카운트+1
    /// NoGo 일때는 누르면 카운트+1
    /// </summary>
    private void Counting()
    {

        SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
    }


    private void ShowScore()
    {
        Avg_GoClickTime = Sum_GoClickTime / Go_clicked;
        AvgResult = Avg_GoClickTime.ToString("F3");

        DMT_Go_missed.text = "놓친 횟수: " + Go_missed + "번";
        DMT_NoGo_clicked.text = "오입력 횟수 : " + NoGo_clicked + "번";
        DMT_AvgResult.text = "평균 입력시간 : " + AvgResult + "초";
        DMT_ProcessCounting.text = "진행도 : " + ProcessCounting + "/3회";
    }

}


